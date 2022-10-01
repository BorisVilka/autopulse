package ru.autopulse05.android.feature.laximo.data.remote

import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapPrimitive
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import ru.autopulse05.android.feature.laximo.data.remote.dto.*
import ru.autopulse05.android.shared.data.ext.toMD5
import java.io.StringReader

@Suppress("SpellCheckingInspection")
@OptIn(DelicateCoroutinesApi::class)
class LaximoRemoteServiceImpl : LaximoRemoteService {

  companion object {
    private fun createAttributeMap(parser: XmlPullParser): Map<String, String?> {
      val map = mutableMapOf<String, String?>()
      for (index in 0 until parser.attributeCount) {
        map[parser.getAttributeName(index)] = parser.getAttributeValue(index)
      }
      return map
    }

    private suspend fun <T> convertPrimitive(
      soapPrimitive: SoapPrimitive,
      onRow: (XmlPullParser) -> T
    ): T {

      val factory = XmlPullParserFactory.newInstance().apply {
        isNamespaceAware = true
      }

      val parser = factory.newPullParser().apply {
        setInput(StringReader(soapPrimitive.value.toString()))
      }

      var result: T? = null

      GlobalScope.launch(Dispatchers.IO) {
        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
          when (parser.eventType) {
            XmlPullParser.START_TAG -> if (parser.name == "row") result = onRow(parser)
          }
          parser.next()
        }
      }.join()

      return result!!
    }

    private suspend fun <T> convertPrimitiveToList(
      soapPrimitive: SoapPrimitive,
      onRow: (XmlPullParser) -> T
    ): List<T> = mutableListOf<T>().apply {

      val factory = XmlPullParserFactory.newInstance().apply {
        isNamespaceAware = true
      }

      val parser = factory.newPullParser().apply {
        setInput(StringReader(soapPrimitive.value.toString()))
      }

      GlobalScope.launch(Dispatchers.IO) {
        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
          when (parser.eventType) {
            XmlPullParser.START_TAG -> if (parser.name == "row") add(onRow(parser))
          }
          parser.next()
        }
      }.join()
    }

    private suspend fun <T> convertPrimitiveToList(
      soapPrimitive: SoapPrimitive,
      onTag: (eventType: Int, XmlPullParser, add: (T) -> Unit) -> Unit
    ): List<T> = mutableListOf<T>().apply {
      val factory = XmlPullParserFactory.newInstance().apply {
        isNamespaceAware = true
      }

      val parser = factory.newPullParser().apply {
        setInput(StringReader(soapPrimitive.value.toString()))
      }

      GlobalScope.launch(Dispatchers.IO) {
        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
          onTag(parser.eventType, parser) { add(it) }
          parser.next()
        }
      }.join()
    }

    private fun createVehicleDto(parser: XmlPullParser): LaximoVehicleDto {
      val map = createAttributeMap(parser)

      return LaximoVehicleDto(
        brand = map["brand"] as String,
        name = map["name"] as String,
        ssd = map["ssd"] as String,
        vehicleId = map["vehicleid"] as String,
        catalog = map["catalog"],
        model = parser.getProperty("modelName") as String?,
        grade = parser.getProperty("grade") as String?,
        transmission = parser.getProperty("transmission") as String?,
        doors = parser.getProperty("doors") as Int?,
        creationRegion = parser.getProperty("creationregion") as String?,
        destinationRegion = parser.getProperty("destinationregion") as String?,
        date = parser.getProperty("date") as String?,
        manufactured = parser.getProperty("manufactured") as Int?,
        frameColor = parser.getProperty("framecolor") as String?,
        trimColor = parser.getProperty("trimcolor") as String?,
        dateFrom = parser.getProperty("datefrom") as String?,
        dateTo = parser.getProperty("dateto") as String?,
        frame = parser.getProperty("frame") as String?,
        frames = parser.getProperty("frames") as String?,
        frameFrom = parser.getProperty("framefrom") as String?,
        frameTo = parser.getProperty("frameto") as String?,
        engine = parser.getProperty("engine") as String?,
        engine1 = parser.getProperty("engine1") as String?,
        engine2 = parser.getProperty("engine2") as String?,
        engineNo = parser.getProperty("engineno") as String?,
        options = parser.getProperty("options") as String?,
        modelYearFrom = parser.getProperty("modelyearnrom") as String?,
        modelYearTo = parser.getProperty("modelyearto") as String?,
        modification = parser.getProperty("modification") as String?,
        description = parser.getProperty("description") as String?
      )
    }

    private fun createDetailDto(parser: XmlPullParser): LaximoDetailDto {
      val map = createAttributeMap(parser)

      return LaximoDetailDto(
        codeOnImage = map["codeonimage"] as String,
        name = map["name"] as String,
        oem = map["oem"],
        ssd = map["ssd"],
        note = parser.getProperty("note") as String?,
        filter = parser.getProperty("filter") as String?,
        flag = parser.getProperty("flag") as String?,
        match = parser.getProperty("match") as String?,
        designation = parser.getProperty("designation") as String?,
        applicableModels = parser.getProperty("applicablemodels") as String?,
        partSpec = parser.getProperty("partspec") as String?,
        color = parser.getProperty("color") as String?,
        shape = parser.getProperty("shape") as String?,
        standard = parser.getProperty("standard") as String?,
        material = parser.getProperty("material") as String?,
        size = parser.getProperty("size") as String?,
        featureDescription = parser.getProperty("featuredescription") as String?,
        prodStart = parser.getProperty("prodstart") as String?,
        prodEnd = parser.getProperty("prodend") as String?
      )
    }

    private fun createCatalogueDto(parser: XmlPullParser): LaximoCatalogDto {
      val map = createAttributeMap(parser)

      return LaximoCatalogDto(
        brand = map["brand"] as String,
        code = map["code"] as String,
        icon = map["icon"] as String,
        name = map["name"] as String
      )
    }

    private fun createApplicationDto(parser: XmlPullParser): LaximoApplicationDto {
      val map = createAttributeMap(parser)
      var model = ""
      var options = ""
      var description = ""
      var period = ""
      //Log.d("TAG","${map.size} "+parser.getAttributeValue(null,"model"))
      //for(i in map.entries) Log.d("TAG", i.value+" "+i.key)
      while(parser.eventType!=XmlPullParser.END_DOCUMENT) {
        when(parser.eventType) {
          XmlPullParser.START_TAG -> {
           // Log.d("TAG",parser.name)
            if(parser.name=="attribute") {
              when(parser.getAttributeValue(0)) {
               "model" -> model = parser.getAttributeValue(2)
                "description" -> description = parser.getAttributeValue(2)
                "options" -> options = parser.getAttributeValue(2)
                "prodPeriod" -> period = parser.getAttributeValue(2)
              }
              //Log.d("TAG","${parser.getAttributeValue(2)} |||")
            }
          }
          XmlPullParser.END_TAG -> {
            if(parser.name=="row") break
          }
        }
        parser.next()
      }
      return LaximoApplicationDto(
        name = map["name"] as String?,
        model = model,
        description = description,
        options = options,
        brand = map["brand"] as String?,
        period = period
      )
    }

    private fun createImageDto(parser: XmlPullParser): LaximoImageDto {
      val map = createAttributeMap(parser)

      return LaximoImageDto(
        code = map["code"] as String,
        x1 = map["x1"] as String,
        y1 = map["y1"] as String
      )
    }

    private fun createCategoryDto(parser: XmlPullParser): LaximoCategoryDto {
      val map = createAttributeMap(parser)

      return LaximoCategoryDto(
        categoryId = (map["categoryid"] as String).toInt(),
        name = map["name"] as String,
        code = map["code"],
        ssd = map["ssd"] as String,
        childrens = (map["childrens"]).toBoolean(),
        parentCategoryId = (map["parentcategoryid"] as String).let {
          if (it != "0" && it.isNotBlank()) it.toInt() else null
        }
      )
    }

    private fun createUnitDto(parser: XmlPullParser): LaximoUnitDto {
      val map = createAttributeMap(parser)

      return LaximoUnitDto(
        unitId = map["unitid"] as String,
        name = map["name"] as String,
        imageUrl = map["imageurl"] as String,
        code = map["code"] as String,
        ssd = map["ssd"] as String
      )
    }

    private fun createVehicleFormFieldOptionDto(
      attributeMap: Map<String, Any?>
    ): LaximoVehicleFormFieldOptionDto = LaximoVehicleFormFieldOptionDto(
      key = attributeMap["key"] as String,
      value = attributeMap["value"] as String
    )

    private fun createVehicleFormFieldDto(
      attributeMap: Map<String, String?>,
      options: List<LaximoVehicleFormFieldOptionDto>
    ): LaximoVehicleFormFieldDto = LaximoVehicleFormFieldDto(
      allowListVehicles = attributeMap["allowlistvehicles"] as String,
      name = attributeMap["name"] as String,
      determined = attributeMap["determined"] as String,
      value = attributeMap["value"],
      ssd = attributeMap["ssd"],
      type = attributeMap["type"],
      automatic = attributeMap["automatic"] as String,
      options = options
    )
  }

  private val httpTransportSE = HttpTransportSE(LaximoHttpRoutes.URL)

  private suspend fun call(
    login: String,
    password: String,
    method: String,
    vararg args: Pair<String, String>
  ): SoapPrimitive {
    val requestValueBuilder = StringBuilder("$method:")

    var idx = 0
    do {
      val (key, value) = args[idx]
      requestValueBuilder.append("$key=$value")
      if (idx != args.lastIndex) requestValueBuilder.append("|")
    } while (args.size > ++idx)

    val requestValue = requestValueBuilder.toString()
    val properties = listOf(
      PropertyInfo().apply {
        name = "request"
        value = requestValue
        type = String::class
      },
      PropertyInfo().apply {
        name = "login"
        value = login
        type = String::class
      },
      PropertyInfo().apply {
        name = "hmac"
        value = "$requestValue$password".toMD5()
        type = String::class
      }
    )

    val request = SoapObject(LaximoHttpRoutes.NAMESPACE, LaximoHttpRoutes.LOGIN).apply {
      properties.forEach { this.addProperty(it) }
    }
    val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11).apply {
      dotNet = true
      setOutputSoapObject(request)
    }

    GlobalScope.launch(Dispatchers.IO) {
      httpTransportSE.call(LaximoHttpRoutes.SOAP_ACTION, envelope)
    }.join()

    return envelope.response as SoapPrimitive
  }

  override suspend fun getCatalogs(
    login: String,
    password: String,
    ssd: String,
    locale: String
  ): List<LaximoCatalogDto> {
    val response = call(
      login = login,
      password = password,
      method = LaximoHttpRoutes.LIST_CATALOGS,
      Pair("Locale", locale),
      Pair("ssd", ssd)
    )
    return convertPrimitiveToList(response, ::createCatalogueDto)
  }

  override suspend fun getVehiclesByVin(
    login: String,
    password: String,
    vin: String,
    locale: String
  ): List<LaximoVehicleDto> {
    val response = call(
      login = login,
      password = password,
      method = LaximoHttpRoutes.FIND_VEHICLE_BY_VIN,
      Pair("Locale", locale),
      Pair("IdentString", vin),
    )
    return convertPrimitiveToList(response, ::createVehicleDto)
  }

  override suspend fun getVehiclesByFrame(
    login: String,
    password: String,
    frameName: String,
    frameNumber: String,
    locale: String
  ): List<LaximoVehicleDto> {
    val response = call(
      login = login,
      password = password,
      method = LaximoHttpRoutes.FIND_VEHICLE_BY_FRAME,
      Pair("Locale", locale),
      Pair("Frame", frameName),
      Pair("FrameNo", frameNumber),
      Pair("Localized", "true")
    )
    return convertPrimitiveToList(response, ::createVehicleDto)
  }

  override suspend fun getCategories(
    login: String,
    password: String,
    catalog: String,
    vehicleId: String,
    categoryId: Int?,
    ssd: String,
    locale: String
  ): List<LaximoCategoryDto> {
    val response = call(
      login = login,
      password = password,
      method = LaximoHttpRoutes.LIST_CATEGORIES,
      args = mutableListOf<Pair<String, String>>().apply {
        add(Pair("Locale", locale))
        add(Pair("Catalog", catalog))
        add(Pair("VehicleId", vehicleId))
        if (categoryId != null) add(Pair("CategoryId", categoryId.toString()))
        add(Pair("ssd", ssd))
      }.toTypedArray()
    )
    return convertPrimitiveToList(response, ::createCategoryDto)
  }

  override suspend fun getUnits(
    login: String,
    password: String,
    catalog: String,
    vehicleId: String,
    categoryId: String,
    ssd: String,
    locale: String
  ): List<LaximoUnitDto> {
    val response = call(
      login = login,
      password = password,
      method = LaximoHttpRoutes.LIST_UNITS,
      Pair("Locale", locale),
      Pair("Catalog", catalog),
      Pair("VehicleId", vehicleId),
      Pair("CategoryId", categoryId),
      Pair("ssd", ssd),
      Pair("Localized", "true")
    )
    return convertPrimitiveToList(response, ::createUnitDto)
  }

  override suspend fun getUnit(
    login: String,
    password: String,
    catalog: String,
    unitId: String,
    ssd: String,
    locale: String
  ): LaximoUnitDto {
    val response = call(
      login = login,
      password = password,
      method = LaximoHttpRoutes.GET_UNIT_INFO,
      Pair("Locale", locale),
      Pair("Catalog", catalog),
      Pair("UnitId", unitId),
      Pair("ssd", ssd),
      Pair("Localized", "true")
    )
    return convertPrimitive(response, ::createUnitDto)
  }

  override suspend fun getVehicleFormFields(
    login: String,
    password: String,
    catalog: String,
    ssd: String,
    locale: String
  ): List<LaximoVehicleFormFieldDto> {
    val response = call(
      login = login,
      password = password,
      method = LaximoHttpRoutes.GET_WIZARD,
      Pair("Locale", locale),
      Pair("Catalog", catalog),
      Pair("ssd", ssd)
    )
    var inMainRow = true
    var fieldMap: Map<String, String?> = mutableMapOf()
    var optionsList = mutableListOf<LaximoVehicleFormFieldOptionDto>()

    return convertPrimitiveToList(response) { eventType, parser, add ->
      when (eventType) {
        XmlPullParser.START_TAG -> if (parser.name == "row") if (inMainRow) {
          fieldMap = createAttributeMap(parser)
          optionsList = mutableListOf()
        } else optionsList.add(createVehicleFormFieldOptionDto(createAttributeMap(parser)))
        else if (parser.name == "options") inMainRow = false
        XmlPullParser.END_TAG -> if (parser.name == "row" && inMainRow) {
          add(
            createVehicleFormFieldDto(attributeMap = fieldMap, options = optionsList)
          )
        } else if (parser.name == "options") inMainRow = true
      }
    }
  }

  override suspend fun getVehicles(
    login: String,
    password: String,
    catalog: String,
    ssd: String,
    locale: String
  ): List<LaximoVehicleDto> {
    val response = call(
      login = login,
      password = password,
      method = LaximoHttpRoutes.FIND_VEHICLE_BY_WIZARD,
      Pair("Locale", locale),
      Pair("Catalog", catalog),
      Pair("Localized", "true"),
      Pair("ssd", ssd)
    )
    return convertPrimitiveToList(response, ::createVehicleDto)
  }

  override suspend fun getDetails(
    login: String,
    password: String,
    catalog: String,
    unitId: String,
    ssd: String,
    locale: String
  ): List<LaximoDetailDto> {
    val response = call(
      login = login,
      password = password,
      method = LaximoHttpRoutes.LIST_DETAIL_BY_UNIT,
      Pair("Locale", locale),
      Pair("Catalog", catalog),
      Pair("UnitId", unitId),
      Pair("Localized", "true"),
      Pair("ssd", ssd)
    )
    return convertPrimitiveToList(response, ::createDetailDto)
  }

  override suspend fun getImageCodes(
    login: String,
    password: String,
    ssd: String,
    catalog: String,
    unitId: String
  ): List<LaximoImageDto> {
    val response = call(
      login = login,
      password = password,
      method = LaximoHttpRoutes.LIST_IMAGE_MAP,
      Pair("Catalog", catalog),
      Pair("UnitId", unitId),
      Pair("ssd", ssd)
    )
    return convertPrimitiveToList(response, ::createImageDto)
  }

  override suspend fun getApplication(
    login: String,
    password: String,
    ssd: String,
    oem: String,
    catalog: String,
    localized: Boolean
  ):List<LaximoApplicationDto> {
    val response = call(
      login = login,
      password = password,
      method = LaximoHttpRoutes.LIST_APPLICATION,
      Pair("OEM", oem),
      Pair("Catalog", catalog),
     )
    Log.d("TAG",response.toString()+" |||")
    return convertPrimitiveToList(response, ::createApplicationDto)
  }
}