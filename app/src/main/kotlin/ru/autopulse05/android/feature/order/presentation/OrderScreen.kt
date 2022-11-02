package ru.autopulse05.android.feature.order.presentation

import android.app.appsearch.AppSearchResult
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.MainActivity
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.cart.domain.model.CartItem
import ru.autopulse05.android.feature.order.data.remote.BOdyObject
import ru.autopulse05.android.feature.order.data.remote.PayService
import ru.autopulse05.android.feature.order.data.remote.PaymentData
import ru.autopulse05.android.feature.order.data.remote.amount
import ru.autopulse05.android.feature.order.data.remote.dto.ConfirmationDto
import ru.autopulse05.android.feature.order.data.remote.dto.KassaType
import ru.autopulse05.android.feature.order.domain.model.WholeOrderMode
import ru.autopulse05.android.feature.order.presentation.components.*
import ru.autopulse05.android.feature.order.presentation.util.OrderEvent
import ru.autopulse05.android.feature.order.presentation.util.OrderShipmentMode
import ru.autopulse05.android.feature.order.presentation.util.OrderUiEvent
import ru.autopulse05.android.feature.shipment.domain.model.ShipmentMethod
import ru.autopulse05.android.feature.store.presentation.util.StoreScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.data.remote.HttpRoutes
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.CheckboxWithText
import ru.autopulse05.android.shared.presentation.components.ExpandableCard
import ru.autopulse05.android.shared.presentation.components.FormTextField
import ru.autopulse05.android.shared.presentation.util.PresentationText
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount
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
        contentExpandedBackgroundColor = Color.BrandYellow.copy(alpha = 0.5f),
        contentCollapsedBackgroundColor = Color.BrandYellow.copy(alpha = 0.5f)
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
          if(state.shipmentAddress.value.isEmpty() && state.shipmentMode != OrderShipmentMode.Pickup) {
            Toast.makeText(context,"Введите адресс доставки",Toast.LENGTH_LONG).show()
            return@BigButton
          }
          if(state.paymentMethod.value!!.name.contentEquals("Наличные при получении")) {
            viewModel.onEvent(OrderEvent.Submit)
            Toast.makeText(context, "Заказ успешно оформлен!", Toast.LENGTH_LONG).show()
            return@BigButton
          }
          var sum = 0;
          for(i in state.positions.indices) sum+= state.positions[i].price.toInt()*state.positions[i].quantity
          MainActivity.th.start(
            sum.toDouble(),
            "Оплатите заказ онлайн"
          ) { token, type ->
            viewModel.viewModelScope.launch {
              Log.d("TAG", "START" + UUID.randomUUID().toString())
              val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor()
                  .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                  }
                )
                .addInterceptor {
                  val request = it.request()
                  return@addInterceptor it.proceed(
                    request
                      .newBuilder()
                      .header(
                        "Authorization",
                        Credentials.basic(
                          "550143",
                          "live_CpHf1Essfi-3iBvtn_x1LpqyDd29JdMTuGmGEcE63iI"
                        )
                      )
                      //.addHeader("500143","live_NTUwMTQzmq1-aBdFlUos-GRoVOo8NvoNFNIuBVgyiuU")
                      .addHeader("Idempotence-Key", UUID.randomUUID().toString())
                      .addHeader("Content-Type", "application/json")
                      .build()
                  )
                }
                .build()
              val retrofit = Retrofit.Builder()
                .baseUrl("https://api.yookassa.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
              //val s = retrofit.create(PayService::class.java).test()
              val url = retrofit.create(PayService::class.java).get(
                body = BOdyObject(
                  amount = amount(
                    value = sum.toDouble().toString(),
                    currency = "RUB"
                  ),
                  payment_token = token,
                 )
              )
              if(url!=null) {
                if(!url.status.contentEquals("pending")) return@launch
                MainActivity.th.confirm(
                  url.confirmation.confirmation_url
                ) {
                  viewModel.viewModelScope.launch {
                    viewModel.state = viewModel.state.copy(isLoading = true)
                    var dto = retrofit.create(PayService::class.java).paymentInfo(url.id)
                    Log.d("TAG", "DTO GET " + dto.id + " " + dto.status)
                    while(dto.status.contentEquals("pending")) {
                      dto = retrofit.create(PayService::class.java).paymentInfo(url.id)
                    }
                    if (dto.status.equals("succeeded") || dto.status == "succeeded") {
                      Toast.makeText(context, "Заказ успешно оформлен!", Toast.LENGTH_LONG).show()
                      viewModel.onEvent(OrderEvent.Submit)
                    }
                    viewModel.state = viewModel.state.copy(isLoading = false)
                  }
                }
              }
            }
          }
          //viewModel.onEvent(OrderEvent.Submit)
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