package ru.autopulse05.android.feature.laximo.data.remote.dto

import ru.autopulse05.android.feature.laximo.domain.model.LaximoUnit


data class LaximoUnitDto(
  val unitId: String,
  val name: String,
  val imageUrl: String,
  val code: String,
  val ssd: String
)

fun LaximoUnitDto.toLaximoUnit() = LaximoUnit(
  id = unitId,
  name = name,
  imageUrl = imageUrl,
  code = code,
  ssd = ssd
)
