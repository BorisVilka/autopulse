package ru.autopulse05.android.feature.order.presentation.util

import ru.autopulse05.android.feature.cart.domain.model.CartItem
import ru.autopulse05.android.feature.order.data.remote.dto.ShipmentOfficeDto
import ru.autopulse05.android.feature.order.domain.model.WholeOrderMode
import ru.autopulse05.android.feature.payment.domain.model.PaymentMethod
import ru.autopulse05.android.feature.shipment.domain.model.ShipmentMethod

sealed class OrderEvent {
  data class InitialValuesChange(val positions: List<CartItem>) : OrderEvent()
  data class PositionsVisibilityChange(val value: Boolean) : OrderEvent()
  data class CommentChange(val value: String) : OrderEvent()
  data class ShipmentModeChange(val value: OrderShipmentMode) : OrderEvent()
  data class ShipmentAddressChange(val value: String) : OrderEvent()
  data class ShipmentMethodChange(val value: ShipmentMethod) : OrderEvent()
  data class OfficeChange(val value: ShipmentOfficeDto) : OrderEvent()
  data class TermsAgreementChange(val value: Boolean) : OrderEvent()
  data class WholeOrderModeChange(val value: WholeOrderMode) : OrderEvent()
  data class PaymentChange(val value: PaymentMethod): OrderEvent()
  object Submit : OrderEvent()
}
