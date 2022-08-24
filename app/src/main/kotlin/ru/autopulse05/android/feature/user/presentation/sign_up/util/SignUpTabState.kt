package ru.autopulse05.android.feature.user.presentation.sign_up.util

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import ru.autopulse05.android.shared.data.ext.fromJson
import ru.autopulse05.android.shared.data.ext.toJson

@Composable
fun rememberSignUpTabState(
  initialValue: SignUpTabs? = null,
  confirmStateChange: (SignUpTabs) -> Boolean = { true }
) = rememberSaveable(saver = SignUpTabState.Saver(confirmStateChange)) {
  SignUpTabState(initialValue, confirmStateChange)
}

class SignUpTabState(
  initialValue: SignUpTabs? = null,
  private val confirmStateChange: (SignUpTabs) -> Boolean = { true }
) {
  private var currentValue by mutableStateOf(initialValue)

  val current: SignUpTabs? get() = currentValue

  private fun set(targetValue: SignUpTabs) {
    currentValue = targetValue
  }

  fun change(value: SignUpTabs) {
    if (confirmStateChange(value)) set(value)
  }

  companion object {
    fun Saver(confirmStateChange: (SignUpTabs) -> Boolean) =
      androidx.compose.runtime.saveable.Saver<SignUpTabState, String>(
        save = { it.currentValue.toJson() },
        restore = { SignUpTabState(it.fromJson(SignUpTabs::class.java), confirmStateChange) }
      )
  }
}