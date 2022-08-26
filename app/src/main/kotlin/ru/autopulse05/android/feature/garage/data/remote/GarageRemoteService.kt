package ru.autopulse05.android.feature.garage.data.remote

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.autopulse05.android.feature.garage.data.remote.dto.AddCarDto
import ru.autopulse05.android.feature.garage.data.remote.dto.CarDto
import ru.autopulse05.android.feature.garage.data.remote.dto.CarDtoList


interface GarageRemoteService {

  @GET(GarageHttpRoutes.GARAGE)
  suspend fun getCars(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String
  ): CarDtoList

  @GET(GarageHttpRoutes.CAR)
  suspend fun getCar(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("carId") carId: String
  ): CarDto

  @POST(GarageHttpRoutes.ADD)
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
  ): AddCarDto

  @POST(GarageHttpRoutes.UPDATE)
  suspend fun updateCar(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("carId") carId: String,
    @Query("name") name: String,
    @Query("comment") comment: String,
    @Query("year") year: String,
    @Query("vin") vin: String,
    @Query("frame") frame: String,
    @Query("mileage") mileage: String,
    @Query("manufacturerId") manufacturerId: String,
    @Query("modelId") modelId: String,
    @Query("modificationId") modificationId: String,
    @Query("vehicleRegPlate") vehicleRegPlate: String
  ): CarDto

  @POST(GarageHttpRoutes.DELETE)
  suspend fun deleteCar(
    @Query("userlogin") login: String,
    @Query("userpsw") passwordHash: String,
    @Query("carId") carId: String
  ): String
}