package ru.autopulse05.android.feature.user.presentation.sign_up.util

import androidx.compose.runtime.Composable
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.user.presentation.sign_up.tabs.SignUpTabPageRetail
import ru.autopulse05.android.feature.user.presentation.sign_up.tabs.SignUpTabPageWholesale
import ru.autopulse05.android.shared.presentation.util.PresentationText

sealed class SignUpTabs(
  val index: Int,
  val title: PresentationText,
  val draw: @Composable () -> Unit
) {
  object Retail: SignUpTabs(
    index = 0,
    title = PresentationText.Resource(id = R.string.retail),
    draw = { SignUpTabPageRetail() }
  )
  object Wholesale: SignUpTabs(
    index = 1,
    title = PresentationText.Resource(id = R.string.wholesale),
    draw = { SignUpTabPageWholesale() }
  )
}
