package ru.autopulse05.android.feature.laximo.data.remote.dto

import ru.autopulse05.android.feature.laximo.domain.model.LaximoCatalog


data class LaximoCatalogDto(
  val code: String,
  val brand: String,
  val icon: String,
  val name: String
)

fun LaximoCatalogDto.toLaximoCatalog() = LaximoCatalog(
  code = code,
  brand = brand,
  icon = icon,
  name = name
)
