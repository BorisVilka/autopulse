package ru.autopulse05.android.feature.vin.presentation.list

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.autopulse05.android.core.presentation.ui.theme.*
import ru.autopulse05.android.feature.cart.presentation.util.CartScreens
import ru.autopulse05.android.feature.product.presentation.list.util.ProductListUiEvent
import ru.autopulse05.android.feature.product.presentation.util.ProductScreens
import ru.autopulse05.android.feature.store.presentation.util.StoreScreens
import ru.autopulse05.android.feature.vin.presentation.util.VinScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.presentation.LoadingScreen
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.util.PresentationText
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun VinListScreen(
    navController: NavController,
    viewModel: VinListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is ProductListUiEvent.GoToProductDetails -> navController.navigate(
                    ProductScreens.Detail.withArgs("?product=${event.value.toJson()}")
                )
                is ProductListUiEvent.GoToBasket -> navController.navigate(
                    CartScreens.Main.route
                )
                is ProductListUiEvent.Toast -> Toast.makeText(context, event.text, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    Column(modifier = Modifier.padding(SpaceNormal)) {

        Text(
            text = "Мои vin-запросы",
            style = MaterialTheme.typography.subtitle1,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(SpaceNormal))

        BigButton(onClick = {
            navController.navigate(VinScreens.Parts.route)
        }, text = PresentationText.Dynamic("Отправить запрос по VIN"))

        Spacer(modifier = Modifier.height(SpaceNormal))


        LoadingScreen(isLoading = viewModel.state.list==null) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {

                itemsIndexed(viewModel.state.list!!) {ind, it ->
                    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                    val date = LocalDateTime.ofEpochSecond(it.createdDateTime,0, ZoneOffset.UTC)
                    val color = when(it.state) {
                         "completed" -> Color.Green
                        "processing" -> Color.Blue
                        else -> Color.Cyan
                    }
                    .copy(alpha = 0.5f)
                    val label = when(it.state) {
                        "completed" -> "Выполнено"
                        "processing" -> "В работе"
                        else -> "Новый"
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = SpaceSmall)
                        .background(color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small)
                        .clickable {
                            navController.navigate(VinScreens.Detail.withArgs("?details=${it.toJson()}"))
                        }
                        .padding(SpaceSmall)
                    ) {
                        Row(
                        ) {

                            Text(text = "№\n${it._id}")

                            Spacer(modifier = Modifier.width(SpaceNormal))

                            Text(text = "Дата\n${formatter.format(date)}")


                        }
                        Spacer(modifier = Modifier.height(SpaceNormal))

                        Text(text = "Статус\n${label}",
                        modifier = Modifier.background(color)
                            .fillMaxSize()
                            .padding(SpaceExtraSmall)
                        )

                        Spacer(modifier = Modifier.height(SpaceNormal))

                        Text(text = "Автомобиль\n${it.carInfo!!.brand} ${it.carInfo.model}")

                        Spacer(modifier = Modifier.height(SpaceNormal))

                        Text(text = if(it.carInfo.vin.isEmpty()) "Frame: ${it.carInfo.frame}" else "VIN: ${it.carInfo.vin}")
                    }
                    //Divider(thickness = 1.dp,color = MaterialTheme.colors.onSecondary)
                }
            }
        }

    }

}