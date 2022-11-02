package ru.autopulse05.android.feature.product.presentation.list.util

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import ru.autopulse05.android.feature.laximo.domain.model.LaximoApplicationInfo
import ru.autopulse05.android.feature.product.domain.model.Product
import ru.autopulse05.android.feature.product.domain.util.OrderType

data class ProductListState(
    val products: PersistentMap<Product, Int> = persistentMapOf(),
    val showBasketDialogs: PersistentMap<Product, Boolean> = persistentMapOf(),
    val showDeliveryDialogs: PersistentMap<Product, Boolean> = persistentMapOf(),
    val showInfoDialogs: PersistentMap<Product,Boolean> = persistentMapOf(),
    val isNotFound: Boolean = false,
    val isLoading: Boolean = true,
    val number: String = "",
    val brand: String = "",
    val sortObject: Int? = null,
    val brands: List<String> = listOf(),
    val chooised: List<String> = listOf(),
    val showBrands: Boolean = false,
    val order: OrderType = OrderType.Ascending,
    val deliveryProbabilityDialogIsShowing: Boolean = false,
    val availabilityFilterIsShowing: Boolean = false,
    val priceFilterIsShowing: Boolean = false,
    val isShowingBasket: Boolean = false,
    val showApplication: Boolean = false,
    val applications: MutableMap<String,MutableMap<String, MutableList<LaximoApplicationInfo>>> = mutableMapOf(),
    val choicedBrand: String = "",
    val choicedModel: String = "",
    val list: MutableList<LaximoApplicationInfo> = mutableListOf(),
)