package ru.autopulse05.android.feature.search.presentation.util

import ru.autopulse05.android.feature.search.domain.model.HistoryItem
import ru.autopulse05.android.feature.search.domain.model.SearchTip

sealed class SearchEvent {
  data class InitialValuesChange(val number: String): SearchEvent()
  data class NumberChange(val value: String): SearchEvent()
  data class TipClick(val value: SearchTip): SearchEvent()
  data class HistoryItemClick(val value: HistoryItem): SearchEvent()
}