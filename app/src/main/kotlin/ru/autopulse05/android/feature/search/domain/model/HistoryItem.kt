package ru.autopulse05.android.feature.search.domain.model


data class HistoryItem(
  val brand: String,
  val number: String,
  val numberFix: String,
  val description: String?,
  val datetime: String
)