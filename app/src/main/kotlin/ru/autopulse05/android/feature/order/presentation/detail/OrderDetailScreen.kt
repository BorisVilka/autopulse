package ru.autopulse05.android.feature.order.presentation.detail

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
import androidx.navigation.NavController
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.order.domain.model.Order
import ru.autopulse05.android.feature.order.presentation.detail.components.DetailItem
import ru.autopulse05.android.feature.order.presentation.detail.util.OrderDetailEvent
import ru.autopulse05.android.shared.presentation.components.ExpandableCard
import ru.autopulse05.android.shared.presentation.util.PresentationText


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

    Column(modifier = Modifier
        .padding(SpaceSmall)
        .verticalScroll(rememberScrollState())) {

        Text(
            text = "Заказ №${order.number} от ${order.date}",
            fontWeight = FontWeight.Bold,
        )
        
        Spacer(modifier = Modifier.height(SpaceLarge))
        
        Column(modifier = Modifier
            .padding(SpaceSmall)
            .background(MaterialTheme.colors.background)) {

            Row {
                Text(
                    text = "Адрес доставки: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = (if(order.deliveryType==null) order.deliveryOffice.orEmpty() else order.deliveryAddress.orEmpty())
                )
            }

            Row {
                Text(
                    text = "Тип доставки: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = (if(order.deliveryType==null) order.deliveryAddress.orEmpty() else order.deliveryType.orEmpty())
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

        Column(modifier = Modifier
            .padding(SpaceSmall)
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)) {

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
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

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
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

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Итого: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = (order.debt.orEmpty())
                )
            }
        }

        Spacer(modifier = Modifier.height(SpaceNormal))
    }
    

}