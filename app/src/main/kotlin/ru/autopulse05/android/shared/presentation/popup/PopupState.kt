package ru.autopulse05.android.shared.presentation.popup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun rememberPopupState(
  initialValue: PopupValue = PopupValue.Closed,
  confirmStateChange: (PopupValue) -> Boolean = { true }
) = rememberSaveable(saver = PopupState.Saver(confirmStateChange)) {
  PopupState(initialValue, confirmStateChange)
}

enum class PopupValue {
  Closed,
  Open
}

class PopupState(
  initialValue: PopupValue = PopupValue.Closed,
  private val confirmStateChange: (PopupValue) -> Boolean = { true }
) {
  private var currentValue by mutableStateOf(initialValue)

  val isOpen: Boolean get() = currentValue == PopupValue.Open
  val isClosed: Boolean get() = currentValue == PopupValue.Closed

  private fun animateTo(targetValue: PopupValue) {
    currentValue = targetValue
  }

  fun open() {
    val targetValue = PopupValue.Open
    if (confirmStateChange(targetValue)) animateTo(targetValue)
  }

  fun close() {
    val targetValue = PopupValue.Closed
    if (confirmStateChange(targetValue)) animateTo(targetValue)
  }

  companion object {
    fun Saver(confirmStateChange: (PopupValue) -> Boolean) =
      Saver<PopupState, PopupValue>(
        save = { it.currentValue },
        restore = { PopupState(it, confirmStateChange) }
      )
  }
}