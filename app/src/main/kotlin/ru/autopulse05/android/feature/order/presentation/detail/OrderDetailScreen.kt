package ru.autopulse05.android.feature.order.presentation.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.autopulse05.android.core.presentation.MainActivity
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.order.data.remote.BOdyObject
import ru.autopulse05.android.feature.order.data.remote.PayService
import ru.autopulse05.android.feature.order.data.remote.amount
import ru.autopulse05.android.feature.order.domain.model.Order
import ru.autopulse05.android.feature.order.domain.use_case.OrderObject
import ru.autopulse05.android.feature.order.presentation.detail.components.DetailItem
import ru.autopulse05.android.feature.order.presentation.detail.util.OrderDetailEvent
import ru.autopulse05.android.feature.order.presentation.util.OrderEvent
import ru.autopulse05.android.feature.order.presentation.util.OrderUiEvent
import ru.autopulse05.android.feature.store.presentation.util.StoreScreens
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.ExpandableCard
import ru.autopulse05.android.shared.presentation.util.PresentationText
import java.util.*


@Composable
fun OrderDetailScreen(
    navController: NavController,
    order: Order,
    viewModel: OrderDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.onEvent(
            OrderDetailEvent.InitialValuesChange(
                positions = order.positions!!
            )
        )
    }
    LaunchedEffect(key1 = context) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is OrderUiEvent.Success -> navController.popBackStack()
                is OrderUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    LoadingScreen(
        isLoading = viewModel.state.isLoading
    ) {
        Column(
            modifier = Modifier
                .padding(SpaceSmall)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Заказ №${order.number} от ${order.date}",
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(SpaceLarge))

            Column(
                modifier = Modifier
                    .padding(SpaceSmall)
                    .background(MaterialTheme.colors.background)
            ) {

                Row {
                    Text(
                        text = "Адрес доставки: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = (if (order.deliveryType == null) order.deliveryOffice.orEmpty() else order.deliveryAddress.orEmpty())
                    )
                }

                Row {
                    Text(
                        text = "Тип доставки: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = (if (order.deliveryType == null) order.deliveryAddress.orEmpty() else order.deliveryType.orEmpty())
                    )
                }

                Row {
                    Text(
                        text = "Комментарий: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = (order.comment.orEmpty())
                    )
                }

                Row {
                    Text(
                        text = "Статус: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = order.status
                    )
                }
            }

            Spacer(modifier = Modifier.height(SpaceLarge))

            ExpandableCard(
                title = PresentationText.Dynamic("Товары"),
                onClick = { value ->
                    viewModel.onEvent(OrderDetailEvent.PositionsVisibilityChange(value = value))
                },
                expanded = viewModel.state.isPositionsVisible,
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
                    viewModel.state.positions.forEach { item ->
                        DetailItem(item = item)

                        Spacer(modifier = Modifier.height(SpaceSmall))
                    }
                }
            }



            Spacer(modifier = Modifier.height(SpaceNormal))

            Column(
                modifier = Modifier
                    .padding(SpaceSmall)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Цена доставки: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = (order.deliveryCost.orEmpty())
                    )
                }
                Spacer(modifier = Modifier.height(SpaceSmall))
                Divider(color = MaterialTheme.colors.onSecondary)
                Spacer(modifier = Modifier.height(SpaceSmall))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Цена заказа: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = (order.sum.orEmpty())
                    )
                }

                Spacer(modifier = Modifier.height(SpaceSmall))
                Divider(color = MaterialTheme.colors.onSecondary)
                Spacer(modifier = Modifier.height(SpaceSmall))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Итого: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = (order.debt.orEmpty())
                    )
                }
            }

            if (order.statusId.contentEquals("102425")) {
                Spacer(modifier = Modifier.height(SpaceNormal))
                BigButton(
                    onClick =
                    {
                        MainActivity.th.start(
                            order.sum!!.replace(',','.').replace(" ","").toDouble(),
                            "Оплата заказа № ${order.number}"
                        ) { token, type ->
                            viewModel.viewModelScope.launch {
                                Log.d("TAG", "START" + UUID.randomUUID().toString())
                                val client = OkHttpClient.Builder()
                                    .addInterceptor(
                                        HttpLoggingInterceptor()
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
                                                .addHeader(
                                                    "Idempotence-Key",
                                                    UUID.randomUUID().toString()
                                                )
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
                                            value = order.sum.replace(',','.'),
                                            currency = "RUB"
                                        ),
                                        payment_token = token,
                                    )
                                )
                               if(url!=null) {
                                   if (!url.status.contentEquals("pending")) return@launch
                                   MainActivity.th.confirm(
                                       url.confirmation.confirmation_url
                                   ) {
                                       viewModel.viewModelScope.launch {
                                           viewModel.state = viewModel.state.copy(isLoading = true)
                                           var dto = retrofit.create(PayService::class.java)
                                               .paymentInfo(url.id)
                                           Log.d("TAG", "DTO GET " + dto.id + " " + dto.status)
                                           while (dto.status.contentEquals("pending")) {
                                               dto = retrofit.create(PayService::class.java)
                                                   .paymentInfo(url.id)
                                           }
                                           if (dto.status.equals("succeeded") || dto.status == "succeeded") {
                                               Toast.makeText(
                                                   context,
                                                   "Заказ успешно оформлен!",
                                                   Toast.LENGTH_LONG
                                               ).show()
                                               viewModel.addPay(OrderObject(date = order.date.orEmpty(),sum = order.sum.replace(',','.').toDouble().toInt()),order)
                                           }
                                           viewModel.state = viewModel.state.copy(isLoading = false)
                                       }
                                   }
                               }
                            }
                        }

                    },
                    text = PresentationText.Dynamic("Оплатить")
                )
            }

            Spacer(modifier = Modifier.height(SpaceNormal))
        }
    }
}