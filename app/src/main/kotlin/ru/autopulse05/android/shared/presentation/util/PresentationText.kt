package ru.autopulse05.android.shared.presentation.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class PresentationText {
  data class Dynamic(val value: String) : PresentationText()

  class Resource(
    @StringRes val id: Int,
    val append: String = "",
    vararg val args: Any
  ) : PresentationText()

  @Composable
  fun asString(): String {
    return when(this) {
      is Dynamic -> value
      is Resource -> stringResource(id = id, *args) + append
    }
  }
}