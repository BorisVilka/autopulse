package ru.autopulse05.android.feature.order.data.remote

object OrderHttpRoutes {
  const val ORDERS = "orders"
  const val ORDER = "basket/order"
  const val INSTANT = "$ORDERS/instant"
  const val CANCEL = "$ORDERS/cancelPosition"
  const val CREATE = "ts/orders/createByCart"
  const val LIST = "ts/orders/list"
  const val PAYMENT = "basket/paymentMethods"
  const val OFFICE = "basket/shipmentOffices"
  const val ADDRESS = "basket/shipmentAddress"

  // Refunds
  const val COMPLAINTS = "ts/customerComplaints/getPositions"
  const val CREATE_COMPLAINT = "ts/customerComplaints/create"

  // Pickings
  const val PICKINGS = "ts/orderPickings/getGoods"
}