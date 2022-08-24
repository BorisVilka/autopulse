package ru.autopulse05.android.feature.product.data.remote.dto

import com.google.gson.annotations.SerializedName
import ru.autopulse05.android.feature.product.domain.model.ProductInfo
import ru.autopulse05.android.shared.data.remote.dto.ImageDto
import ru.autopulse05.android.shared.data.remote.dto.toImage

data class ProductInfoDto(
  val brand: String,
  val number: String,
  @SerializedName("outer_number") val outerNumber: String?,
  val properties: PropertiesDto,
  val crosses: List<CrosseDto>,
  val images: List<ImageDto>
)

fun ProductInfoDto.toProductInfo() =
  ProductInfo(
    brand = brand,
    number = number,
    outerNumber = outerNumber,
    properties = properties.toProperties(),
    crosses = crosses.map { it.toCrosse() },
    images = images.map { it.toImage() }
  )