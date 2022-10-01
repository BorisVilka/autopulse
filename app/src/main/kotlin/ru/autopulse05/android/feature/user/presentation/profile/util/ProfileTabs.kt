package ru.autopulse05.android.feature.user.presentation.profile.util

import androidx.compose.runtime.Composable
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.user.presentation.profile.tabs.*
import ru.autopulse05.android.shared.presentation.util.PresentationText

sealed class ProfileTabs(
  val index: Int,
  val title: PresentationText,
  val draw: @Composable () -> Unit
) {
  object Orders : ProfileTabs(
    index = 0,
    title = PresentationText.Resource(R.string.orders),
    draw = { ProfileTabPageOrders() }
  )

  object Pickings : ProfileTabs(
    index = 1,
    title = PresentationText.Resource(R.string.shipments),
    draw = { ProfileTabPagePickings() }
  )

  object Refunds : ProfileTabs(
    index = 2,
    title = PresentationText.Resource(R.string.refunds),
    draw = { ProfileTabPageRefunds() }
  )

  object Garage : ProfileTabs(
    index = 4,
    title = PresentationText.Resource(R.string.garage),
    draw = { ProfileTabPageGarage() }
  )

  object Data : ProfileTabs(
    index = 6,
    title = PresentationText.Resource(R.string.personal_data),
    draw = { ProfileTabPageData() }
  )

  object Payments : ProfileTabs(
    index = 7,
    title = PresentationText.Dynamic("Платежи"),
    draw = { ProfileTabPagePayments() }
  )
}