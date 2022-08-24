package ru.autopulse05.android.feature.product.data.remote.dto

import ru.autopulse05.android.feature.product.domain.model.Properties

data class PropertiesDto(
  val boil_temp: String?,
  val coolant_color: String?,
  val coolant_type: String?,
  val descr: String?,
  val ean: String?,
  val ext_descr: String?,
  val frost_temp: String?,
  val goods_group: String?,
  val height_mm: String?,
  val length_mm: String?,
  val liquid_volume: String?,
  val volume: String?,
  val weight: String?,
  val width_mm: String?
)

fun PropertiesDto.toProperties() = Properties(
  boil_temp = boil_temp,
  coolant_color = coolant_color,
  coolant_type = coolant_type,
  descr = descr,
  ean = ean,
  ext_descr = ext_descr,
  frost_temp = frost_temp,
  goods_group = goods_group,
  height_mm = height_mm,
  length_mm = length_mm,
  liquid_volume = liquid_volume,
  volume = volume,
  weight = weight,
  width_mm = width_mm
)