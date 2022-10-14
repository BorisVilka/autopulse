package ru.autopulse05.android.feature.laximo.data.remote

import ru.autopulse05.android.feature.laximo.data.remote.dto.*

interface LaximoRemoteService {
  suspend fun getCatalogs(
    login: String,
    password: String,
    ssd: String = "",
    locale: String = "ru_RU"
  ): List<LaximoCatalogDto>

  suspend fun getVehiclesByVin(
    login: String,
    password: String,
    vin: String,
    locale: String = "ru_RU"
  ): List<LaximoVehicleDto>

  suspend fun getVehiclesByFrame(
    login: String,
    password: String,
    frameName: String,
    frameNumber: String,
    locale: String = "ru_RU"
  ): List<LaximoVehicleDto>

  suspend fun getCategories(
    login: String,
    password: String,
    catalog: String,
    vehicleId: String,
    categoryId: Int? = null,
    ssd: String,
    locale: String = "ru_RU"
  ): List<LaximoCategoryDto>

  suspend fun getUnits(
    login: String,
    password: String,
    catalog: String,
    vehicleId: String,
    categoryId: String = "",
    ssd: String,
    locale: String = "ru_RU"
  ): List<LaximoUnitDto>

  suspend fun getUnit(
    login: String,
    password: String,
    catalog: String,
    unitId: String,
    ssd: String,
    locale: String = "ru_RU"
  ): LaximoUnitDto

  suspend fun getVehicleFormFields(
    login: String,
    password: String,
    catalog: String,
    ssd: String = "",
    locale: String = "ru_RU"
  ): List<LaximoVehicleFormFieldDto>

  suspend fun getVehicles(
    login: String,
    password: String,
    catalog: String,
    ssd: String,
    locale: String = "ru_RU"
  ): List<LaximoVehicleDto>

  suspend fun getDetails(
    login: String,
    password: String,
    catalog: String,
    unitId: String,
    ssd: String,
    locale: String = "ru_RU"
  ): List<LaximoDetailDto>


  suspend fun getImageCodes(
    login: String,
    password: String,
    ssd: String,
    catalog: String,
    unitId: String,
  ): List<LaximoImageDto>

  suspend fun getApplication(
    login: String,
    password: String,
    ssd: String,
    catalog: String,
    oem: String,
    localized: Boolean = true
  ): List<LaximoApplicationDto>

  suspend fun getQuickGroup(
    login: String,
    password: String,
    catalog: String,
    vehicleId: String,
    ssd: String,
    locale: String = "ru_RU"
  ): List<LaximoQuickGroupDto>

  suspend fun getQuickDetail(
    login: String,
    password: String,
    catalog: String,
    vehicleId: String,
    ssd: String,
    quickGroupId: String,
    locale: String = "ru_RU"
  ): List<LaximoCategoryDto>
}