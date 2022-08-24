package ru.autopulse05.android.feature.search.presentation.util

sealed class SearchUiEvent {
  data class GoToList(val brand: String, val number: String): SearchUiEvent()
  data class Toast(val text: String) : SearchUiEvent()
}