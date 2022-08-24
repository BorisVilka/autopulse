package ru.autopulse05.android.shared.presentation.util

data class FormSelectorFieldData<T>(
  val value: T? = null,
  val items: List<T> = emptyList(),
  val isShowing: Boolean = false,
  val isDisabled: Boolean = false
)