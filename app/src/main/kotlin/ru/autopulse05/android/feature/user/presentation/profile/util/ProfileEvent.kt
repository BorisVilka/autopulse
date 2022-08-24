package ru.autopulse05.android.feature.user.presentation.profile.util

import ru.autopulse05.android.feature.order.domain.model.Order

sealed class ProfileEvent {
  data class TabChange(val value: ProfileTabs) : ProfileEvent()
  object AddCar : ProfileEvent()
  data class OrderDetail(val value: Order): ProfileEvent()
}
