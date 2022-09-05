package ru.autopulse05.android.feature.order.presentation

import android.app.PendingIntent
import android.app.appsearch.AppSearchResult
import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.cart.domain.model.CartItem
import ru.autopulse05.android.feature.order.domain.model.WholeOrderMode
import ru.autopulse05.android.feature.order.presentation.components.*
import ru.autopulse05.android.feature.order.presentation.util.OrderEvent
import ru.autopulse05.android.feature.order.presentation.util.OrderShipmentMode
import ru.autopulse05.android.feature.order.presentation.util.OrderUiEvent
import ru.autopulse05.android.feature.store.presentation.util.StoreScreens
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.CheckboxWithText
import ru.autopulse05.android.shared.presentation.components.ExpandableCard
import ru.autopulse05.android.shared.presentation.components.FormTextField
import ru.autopulse05.android.shared.presentation.util.PresentationText
import ru.yoomoney.sdk.kassa.payments.Checkout
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.*
import java.math.BigDecimal
import java.util.*

@Composable
fun OrderScreen(
  navController: NavController,
  positions: List<CartItem>?,
  viewModel: OrderViewModel = hiltViewModel()
) {

  val state = viewModel.state
  val context = LocalContext.current
  val scrollState = rememberScrollState()
  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartIntentSenderForResult()
  ) {
    if (it.resultCode != AppSearchResult.RESULT_OK) {
      return@rememberLauncherForActivityResult
    }

  }
  LaunchedEffect(key1 = context) {
    viewModel.onEvent(
      OrderEvent.InitialValuesChange(
        positions = positions!!
      )
    )
  }

  LaunchedEffect(key1 = context) {
    viewModel.uiEvents.collect { event ->
      when (event) {
        is OrderUiEvent.Success -> navController.navigate(StoreScreens.Main.route)
        is OrderUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG).show()
      }
    }
  }

  LoadingScreen(
    isLoading = state.isLoading
  ) {
    Column(
      modifier = Modifier
        .padding(SpaceNormal)
        .verticalScroll(scrollState)
        .fillMaxSize()
    ) {
      Text(
        text = PresentationText.Dynamic("Заказ").asString(),
        style = MaterialTheme.typography.h1
      )

      Spacer(modifier = Modifier.height(SpaceSmall))

      ExpandableCard(
        title = PresentationText.Dynamic("Товары"),
        onClick = { value ->
          viewModel.onEvent(OrderEvent.PositionsVisibilityChange(value = value))
        },
        expanded = state.isPositionsVisible,
        shape = MaterialTheme.shapes.small,
        contentExpandedBackgroundColor = MaterialTheme.colors.surface,
        contentCollapsedBackgroundColor = MaterialTheme.colors.surface
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary)
            .padding(SpaceSmall)
        ) {
          state.positions.forEach { item ->
            PositionItem(item = item)

            Spacer(modifier = Modifier.height(SpaceSmall))
          }
        }
      }

      FormTextField(
        fieldName = PresentationText.Resource(id = R.string.comment),
        fieldData = state.comment,
        modifier = Modifier.height(120.dp),
        onValueChange = { value ->
          viewModel.onEvent(OrderEvent.CommentChange(value = value))
        }
      )

      PaymentSection(
        items = state.paymentMethod.items,
        value = state.paymentMethod.value,
        onSelect = {
          viewModel.onEvent(OrderEvent.PaymentChange(it))
        }
      )

      ShipmentTypeSection(
        onSelect = { value ->
          viewModel.onEvent(OrderEvent.ShipmentModeChange(value = value))
        },
        selectedMode = state.shipmentMode
      )

      if (state.shipmentMode == OrderShipmentMode.Delivery) {
        FormTextField(
          fieldName = PresentationText.Dynamic("Адрес доставки"),
          fieldData = state.shipmentAddress,
          onValueChange = { value ->
            viewModel.onEvent(OrderEvent.ShipmentAddressChange(value = value))
          }
        )

        ShipmentMethodSection(
          value = state.shipmentMethod.value,
          items = state.shipmentMethod.items,
          onSelect = { value ->
            viewModel.onEvent(OrderEvent.ShipmentMethodChange(value = value))
          }
        )
      } else {
        OfficeSection(
          value = state.office.value,
          items = state.office.items,
          onSelect = { value ->
            viewModel.onEvent(OrderEvent.OfficeChange(value = value))
          }
        )
      }

      Spacer(modifier = Modifier.height(SpaceNormal))

      CheckboxWithText(
        checked = state.wholeOrderMode == WholeOrderMode.On,
        onClick = {
          viewModel.onEvent(OrderEvent.WholeOrderModeChange(value = if(state.wholeOrderMode==WholeOrderMode.On) WholeOrderMode.Off else WholeOrderMode.On))
        },
        text = PresentationText.Resource(id = R.string.want_whole_order)
      )

      CheckboxWithText(
        checked = state.termsAgreement,
        onClick = {
          viewModel.onEvent(OrderEvent.TermsAgreementChange(value = !state.termsAgreement))
        },
        text = PresentationText.Resource(id = R.string.agreed_with_terms)
      )

      BigButton(
        modifier = Modifier.padding(top = SpaceNormal),
        text = PresentationText.Resource(R.string.confirm_order),
        onClick = {
          val paymentParameters = PaymentParameters(
            amount = Amount(BigDecimal.TEN, Currency.getInstance("RUB")),
            title = "Название товара",
            subtitle = "Описание товара",
            clientApplicationKey = "900933", // ключ для клиентских приложений из личного кабинета ЮKassa
            shopId = "550143", // идентификатор магазина ЮKassa
            savePaymentMethod = SavePaymentMethod.OFF, // флаг выключенного сохранения платежного метода,
            paymentMethodTypes = setOf(
              PaymentMethodType.BANK_CARD,
              PaymentMethodType.SBERBANK
            ), // передан весь список доступных методов оплаты
            gatewayId = null, // gatewayId магазина для платежей Google Pay (необходим в случае, если в способах оплаты есть Google Pay)
            customReturnUrl = "https://yandex.ru", // url страницы (поддерживается только https), на которую надо вернуться после прохождения 3ds.
            userPhoneNumber = "+79038970013", // номер телефона пользователя для автозаполнения поля номера телефона пользователя в SberPay. Поддерживаемый формат данных: "+7XXXXXXXXXX"
            authCenterClientId = "900933" // идентификатор, полученный при регистрации приложения на сайте https://yookassa.ru
          )
          val intent = Checkout.createTokenizeIntent(
            context,
            paymentParameters,
            TestParameters(showLogs = true)
          )
          val pendIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
          launcher.launch(
            IntentSenderRequest.Builder(pendIntent)
              .build()
          )
        },
      )
    }
  }
}

@Preview(
  name = "Order Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun OrderScreenPreview() {
  val navController = rememberNavController()

  OrderScreen(
    navController = navController,
    positions = listOf()
  )
}