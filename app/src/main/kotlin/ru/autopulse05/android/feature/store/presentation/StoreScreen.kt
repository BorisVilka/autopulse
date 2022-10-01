package ru.autopulse05.android.feature.store.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.store.presentation.components.Menu
import ru.autopulse05.android.feature.store.presentation.components.MenuItem
import ru.autopulse05.android.feature.store.presentation.components.PagerItem
import ru.autopulse05.android.feature.store.presentation.components.SupportService
import ru.autopulse05.android.feature.store.presentation.util.MenuItems
import ru.autopulse05.android.feature.store.presentation.util.StoreEvent
import ru.autopulse05.android.feature.store.presentation.util.StoreUiEvent
import ru.autopulse05.android.shared.presentation.util.PresentationText
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StoreScreen(
  navController: NavController,
  viewModel: StoreViewModel = hiltViewModel()
) {
  var pagerState = rememberPagerState()
  val scrollState = rememberScrollState()
  val scope = rememberCoroutineScope()
  val context = LocalContext.current
  val arrT = arrayOf("Работаем 12/7","Лучшие цены","Быстрая доставка")
  val arrD = arrayOf("Отправляйте заказы в любое удобное для вас время, а мы сразу же приступим к исполнению. Быстро заказал - быстро получил.",
    "Покупка у нас в интернет-магазине - это самый короткий путь запчастей от производителя до конечного покупателя.\nНиже наценка - ниже цена.",
    "Экономим ваше время. Самовывоз в любое время или доставка не больше 1 дня с момента заказа.\nБыстро получил - быстро применил.",)

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is StoreUiEvent.MenuItemClick -> navController.navigate(event.value.route)
      }
    }
  }
  LaunchedEffect(Unit) {
    while(true) {
      delay(5.seconds)
      pagerState.animateScrollToPage((pagerState.currentPage+1)%pagerState.pageCount)
    }
  }
  val menuItems = listOf(
    MenuItems.OriginalCatalogue,
    MenuItems.Catalogue,
    MenuItems.RequestByVin
  )

  Column(
    modifier = Modifier.verticalScroll(scrollState)
  ) {

    HorizontalPager(count = 3,
     modifier = Modifier.fillMaxWidth(),
      reverseLayout = false,
      state = pagerState
      ) {

      PagerItem(
        title = arrT[currentPage],
        desc = arrD[currentPage],
        leftClick = {
          scope.launch {
            pagerState.animateScrollToPage(max(0,currentPage-1))

          }
        },
        rightClick = {
          scope.launch {
            pagerState.animateScrollToPage(min(3,currentPage+1))

          }
        }
      )

    }

    Spacer(modifier = Modifier.height(SpaceLarge))

    Menu(
      title = PresentationText.Resource(R.string.spare_parts),
      items = menuItems,
      onItemClick = { item ->
        viewModel.onEvent(StoreEvent.MenuItemClick(value = item))
      },
      modifier = Modifier
        .shadow(elevation = SpaceSmall)
        .background(color = MaterialTheme.colors.surface)
        .padding(top = SpaceNormal)
    )

    Spacer(modifier = Modifier.height(SpaceLarge))

    SupportService(
      modifier = Modifier
        .shadow(elevation = SpaceSmall)
        .background(color = MaterialTheme.colors.surface)
        .padding(SpaceNormal),
      onCallClick = {
        viewModel.onEvent(StoreEvent.CallSupport)
      },
      onWriteClick = {
        viewModel.onEvent(StoreEvent.WriteToSupport)
      }
    )
  }
}

@Preview(
  name = "Store Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun StoreScreenPreview() {
  val navController = rememberNavController()

  StoreScreen(
    navController = navController
  )
}