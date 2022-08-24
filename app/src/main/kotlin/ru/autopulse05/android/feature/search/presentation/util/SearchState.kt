package ru.autopulse05.android.feature.search.presentation.util

import ru.autopulse05.android.feature.search.domain.model.HistoryItem
import ru.autopulse05.android.feature.search.domain.model.SearchTip

data class SearchState(
  val didRequest: Boolean = false,
  val number: String = "",
  val tips: List<SearchTip> = listOf(),
  val showHistory: Boolean = true,
  val historyItems: List<HistoryItem> = listOf()
)