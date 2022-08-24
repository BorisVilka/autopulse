package ru.autopulse05.android.feature.product.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class ProductScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "product"
  }

  object Crosse : ProductScreens("crosse")
  object Detail : ProductScreens("detail")
  object List : ProductScreens("list")
}