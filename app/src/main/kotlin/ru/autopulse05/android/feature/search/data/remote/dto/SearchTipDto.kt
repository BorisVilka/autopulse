package ru.autopulse05.android.feature.search.data.remote.dto

import ru.autopulse05.android.feature.search.domain.model.SearchTip


data class SearchTipDto(
  val brand: String,
  val number: String,
  val description: String
)

fun SearchTipDto.toSearchTip() =
  SearchTip(
    brand = brand,
    number = number,
    description = description
  )