package ru.autopulse05.android.feature.order.presentation.util

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.reflect.TypeToken
import ru.autopulse05.android.feature.cart.domain.model.CartItem
import ru.autopulse05.android.feature.order.presentation.OrderScreen
import ru.autopulse05.android.shared.data.ext.fromJson

private object BasketItemListToken: TypeToken<List<CartItem>>()

fun NavGraphBuilder.orderNavigation(
  navController: NavHostController
) {
  composable(
    route = OrderScreens.Main.route + "?positions={positions}",
    arguments = listOf(
      navArgument("positions") {
        NavType.StringType
      }
    )
  ) { entry ->
    val positions: List<CartItem>? = entry.arguments
      ?.getString("positions")
      ?.fromJson(BasketItemListToken.type)

    OrderScreen(
      navController = navController,
      positions = positions
    )
  }
}