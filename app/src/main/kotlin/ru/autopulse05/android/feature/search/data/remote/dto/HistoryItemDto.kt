package ru.autopulse05.android.feature.search.data.remote.dto

import ru.autopulse05.android.feature.search.domain.model.HistoryItem


data class HistoryItemDto(
  val brand: String,
  val number: String,
  val numberFix: String,
  val description: String?,
  val datetime: String
)

fun HistoryItemDto.toHistoryItem() = HistoryItem(
  brand = brand,
  number = number,
  numberFix = numberFix,
  description = description,
  datetime = datetime
)

