package ru.autopulse05.android.feature.laximo.data.remote

object LaximoHttpRoutes {
  const val NAMESPACE = "http://WebCatalog.Kito.ec"
  const val URL =
    "http://ws.laximo.net/ec.Kito.WebCatalog/services/Catalog.CatalogHttpSoap11Endpoint?wsdl"
  const val SOAP_ACTION =
    "http://ws.laximo.net/ec.Kito.WebCatalog/services/Catalog.CatalogHttpSoap11Endpoint/QueryDataLogin"

  const val LOGIN = "QueryDataLogin"
  const val LIST_CATALOGS = "ListCatalogs"
  const val FIND_VEHICLE_BY_VIN = "FindVehicle"
  const val FIND_VEHICLE_BY_FRAME = "FindVehicle"
  const val LIST_CATEGORIES = "ListCategories"
  const val LIST_UNITS = "ListUnits"
  const val QUICK_GROUP = "ListQuickGroup"
  const val QUICK_DETAIL = "ListQuickDetail"
  const val GET_UNIT_INFO = "GetUnitInfo"
  const val GET_WIZARD = "GetWizard2"
  const val FIND_VEHICLE_BY_WIZARD = "FindVehicleByWizard2"
  const val LIST_DETAIL_BY_UNIT = "ListDetailByUnit"
  const val LIST_IMAGE_MAP = "ListImageMapByUnit"
  const val LIST_APPLICATION = "FindApplicableVehicles"
}