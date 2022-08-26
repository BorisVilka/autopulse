package ru.autopulse05.android.feature.car.data.remote

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.autopulse05.android.feature.car.data.remote.dto.*
import ru.autopulse05.android.shared.data.remote.dto.IntList


interface CarRemoteService {

  // CarTree
  @GET(CarHttpRoutes.MARKS)
  suspend fun getMarks(
    @Query("userlogin") login: String,
    @Query("userpsw") password: String
  ): CarMarkDtoList

  @GET(CarHttpRoutes.MODELS)
  suspend fun getModels(
    @Query("userlogin") login: String,
    @Query("userpsw") password: String,
    @Query("manufacturerId") markId: Int
  ): CarModelDtoList

  @GET(CarHttpRoutes.YEARS)
  suspend fun getYears(
    @Query("userlogin") login: String,
    @Query("userpsw") password: String
  ): IntList

  @GET(CarHttpRoutes.MODIFICATIONS)
  suspend fun getModifications(
    @Query("userlogin") login: String,
    @Query("userpsw") password: String,
    @Query("manufacturerId") markId: Int,
    @Query("modelId") modelId: Int
  ): CarModificationDtoList


  // Garage
  @GET(CarHttpRoutes.GARAGE)
  suspend fun getCars(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String
  ): CarDtoList

  @GET(CarHttpRoutes.GARAGE_CAR)
  suspend fun getCar(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("carId") carId: String
  ): CarDto

  @POST(CarHttpRoutes.GARAGE_ADD)
  suspend fun addCar(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("name") name: String?,
    @Query("comment") comment: String?,
    @Query("year") year: Int?,
    @Query("vin") vin: String?,
    @Query("frame") frame: String?,
    @Query("mileage") mileage: String?,
    @Query("manufacturerId") manufacturerId: Int?,
    @Query("modelId") modelId: Int?,
    @Query("modificationId") modificationId: Int?,
    @Query("vehicleRegPlate") vehicleRegPlate: String?
  ): AddCarToGarageDto

  @POST(CarHttpRoutes.GARAGE_UPDATE)
  suspend fun updateCar(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("carId") carId: String,
    @Query("name") name: String?,
    @Query("comment") comment: String?,
    @Query("year") year: Int?,
    @Query("vin") vin: String?,
    @Query("frame") frame: String?,
    @Query("mileage") mileage: String?,
    @Query("manufacturerId") manufacturerId: Int?,
    @Query("modelId") modelId: Int?,
    @Query("modificationId") modificationId: Int?,
    @Query("vehicleRegPlate") vehicleRegPlate: String?,
  ): CarDto

  @POST(CarHttpRoutes.GARAGE_DELETE)
  suspend fun deleteCar(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("carId") carId: String
  ): String
}