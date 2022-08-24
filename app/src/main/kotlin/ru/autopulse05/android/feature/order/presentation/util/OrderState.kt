package ru.autopulse05.android.feature.order.presentation.util

import ru.autopulse05.android.feature.cart.domain.model.CartItem
import ru.autopulse05.android.feature.order.data.remote.dto.ShipmentOfficeDto
import ru.autopulse05.android.feature.order.domain.model.WholeOrderMode
import ru.autopulse05.android.feature.payment.domain.model.PaymentMethod
import ru.autopulse05.android.feature.shipment.domain.model.ShipmentMethod
import ru.autopulse05.android.feature.user.domain.model.User
import ru.autopulse05.android.shared.presentation.util.FormSelectorFieldData
import ru.autopulse05.android.shared.presentation.util.FormTextFieldData

data class OrderState(
  val user: User? = null,
  val positions: List<CartItem> = listOf(),
  val isPositionsVisible: Boolean = false,
  val isLoading: Boolean = true,
  val comment: FormTextFieldData = FormTextFieldData(),
  val shipmentAddress: FormTextFieldData = FormTextFieldData(),
  val paymentMethod: FormSelectorFieldData<PaymentMethod> = FormSelectorFieldData(),
  val office: FormSelectorFieldData<ShipmentOfficeDto> = FormSelectorFieldData(),
  val shipmentMethod: FormSelectorFieldData<ShipmentMethod> = FormSelectorFieldData(),
  val termsAgreement: Boolean = true,
  val wholeOrderMode: WholeOrderMode = WholeOrderMode.Off,
  val shipmentMode: OrderShipmentMode = OrderShipmentMode.Pickup
)