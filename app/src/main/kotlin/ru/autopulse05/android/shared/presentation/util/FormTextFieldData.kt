package ru.autopulse05.android.shared.presentation.util

data class FormTextFieldData(
  val value: String = "",
  val error: String? = null,
  val isRequired: Boolean = true,
  val isDisabled: Boolean = false
) {
  val hasError: Boolean get() = error != null
}