package ru.autopulse05.android.feature.user.presentation.profile.util

import ru.autopulse05.android.feature.car.domain.model.Car
import ru.autopulse05.android.feature.laximo.domain.model.LaximoVehicle
import ru.autopulse05.android.feature.laximo.presentation.catalogs.util.CatalogsUiEvent
import ru.autopulse05.android.feature.order.domain.model.Order


sealed class ProfileUiEvent {
  data class TabChange(val value: ProfileTabs) : ProfileUiEvent()
  data class EditCar(val value: Car) : ProfileUiEvent()
  data class OrderDetail(val value: Order): ProfileUiEvent()
  data class GoToVehicles(val vehicles: List<LaximoVehicle>) : ProfileUiEvent()
  object OpenVinRequest: ProfileUiEvent()
  object AddCar : ProfileUiEvent()
  object SignOut : ProfileUiEvent()
  object NotSignedIn : ProfileUiEvent()
  data class Toast(val text: String): ProfileUiEvent()
}