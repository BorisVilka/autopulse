package ru.autopulse05.android.feature.basket.data.remote.dto

import com.google.gson.annotations.SerializedName
import ru.autopulse05.android.feature.basket.domain.model.BasketOptionsItem


data class BasketOptionsItemDto(
  @SerializedName("disallow_new_shipment_address") val disallowNewShipmentAddress: Int,
  @SerializedName("self_shipment") val selfShipment: String
)

fun BasketOptionsItemDto.toBasketOptionsItem() = BasketOptionsItem(
  disallowNewShipmentAddress = disallowNewShipmentAddress,
  selfShipment = selfShipment
)