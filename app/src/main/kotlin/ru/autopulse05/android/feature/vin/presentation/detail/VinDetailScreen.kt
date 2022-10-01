package ru.autopulse05.android.feature.vin.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.order.presentation.components.PositionItem
import ru.autopulse05.android.feature.order.presentation.util.OrderEvent
import ru.autopulse05.android.feature.product.presentation.util.ProductScreens
import ru.autopulse05.android.feature.vin.data.remote.dto.VinDto
import ru.autopulse05.android.feature.vin.presentation.list.VinListViewModel
import ru.autopulse05.android.feature.vin.presentation.util.VinScreens
import ru.autopulse05.android.shared.data.ext.toJson
import ru.autopulse05.android.shared.presentation.components.BigButton
import ru.autopulse05.android.shared.presentation.components.BrandNumberSection
import ru.autopulse05.android.shared.presentation.components.ExpandableCard
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun VinDetailScreen(
    navController: NavController,
    details: VinDto,
    viewModel: VinDetailViewModel = hiltViewModel()
) {
    viewModel.state.id = details._id.toString()
    val color = when(details.state) {
        "completed" -> Color.Green
        "processing" -> Color.Blue
        else -> Color.Cyan
    }
        .copy(alpha = 0.5f)
    val label = when(details.state) {
        "completed" -> "Выполнено"
        "processing" -> "В работе"
        else -> "Новый"
    }
    Column(modifier = Modifier
        .padding(SpaceSmall)
        .verticalScroll(rememberScrollState())
    ) {
        Text(text = "VIN запрос № ${details._id}",
            style = MaterialTheme.typography.subtitle1
        )

        Spacer(modifier = Modifier.height(SpaceNormal))

        Row(modifier = Modifier
            .background(color)
            .fillMaxWidth()
            .padding(SpaceNormal),

            ) {
            Icon(painter = painterResource(id = R.drawable.ic_outline_info_24),
                contentDescription = PresentationText.Resource(id = R.string.profile).asString(),
            modifier = Modifier.align(Alignment.CenterVertically))
            Spacer(modifier = Modifier
                .width(SpaceNormal)
                .align(Alignment.CenterVertically))
            Text(text = "Статус: ${label}")
        }

        Spacer(modifier = Modifier.height(SpaceNormal))

        Column(
            modifier = Modifier
                .shadow(elevation = SpaceSmall)
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = MaterialTheme.shapes.small
                )
                .padding(SpaceNormal)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(SpaceNormal))

            Text(text = "${details.carInfo!!.brand} ${details.carInfo.model}")

            Spacer(modifier = Modifier.height(SpaceNormal))

            Text(text = "${details.carInfo.year}")

            Spacer(modifier = Modifier.height(SpaceNormal))

            Text(text = if(details.carInfo.vin.isEmpty()) "Frame: ${details.carInfo.frame}" else "VIN: ${details.carInfo.vin}")
        }

        Spacer(modifier = Modifier.height(SpaceNormal))

        ExpandableCard(
            title = PresentationText.Dynamic("Вы просили подобрать:"),
            onClick = { value ->
                viewModel.state = viewModel.state.copy(
                    partsVisible = !viewModel.state.partsVisible
                )
            },
            expanded = viewModel.state.partsVisible,
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
                details.parts.filter {
                    it.offers==null
                }.onEach {
                    Text(text = it.query,
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = SpaceSmall)
                            .background(
                                color = MaterialTheme.colors.surface,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(SpaceSmall)
                        )
                    Spacer(modifier = Modifier.height(SpaceSmall))
                }
            }
        }

        Spacer(modifier = Modifier.height(SpaceNormal))

        if(details.expertReply!=null) {
            Text(text = "Комментарий эксперта: ${details.expertReply}"
            , style = MaterialTheme.typography.body1)
        }

        Spacer(modifier = Modifier.height(SpaceNormal))

        ExpandableCard(
            title = PresentationText.Dynamic("Результат подбора:"),
            onClick = { value ->
                viewModel.state = viewModel.state.copy(
                    offersVisible = !viewModel.state.offersVisible
                )
            },
            expanded = viewModel.state.offersVisible,
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
                details.parts.filter {
                    it.offers!=null
                }.onEach {
                    it.offers!!.onEach {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = SpaceSmall)
                            .background(
                                color = MaterialTheme.colors.surface,
                                shape = MaterialTheme.shapes.small
                            ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.padding(SpaceSmall)) {
                                BrandNumberSection(brand = it.brand, number = it.number)
                                Spacer(modifier = Modifier.height(SpaceSmall))
                                Text(text = it.descr)
                            }
                            
                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(SpaceSmall)
                            ) {
                                
                                Text(text = "Кол-во: ${it.quantity} шт.",
                                )
                                Spacer(modifier = Modifier.height(SpaceSmall))

                                Text(text = "Цены и\nналичие",
                                    style =  TextStyle(
                                        color = Color(0xff64B5F6),
                                        fontSize = 16.sp,
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .clickable {
                                            navController.navigate(
                                                ProductScreens.List.withArgs(
                                                    "?brand=${it.brand}",
                                                    "&number=${it.number}",
                                                )
                                            )
                                        }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(SpaceSmall))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(SpaceNormal))

        BigButton(onClick = {
            navController.navigate(VinScreens.Chat.withArgs("?chat=${details.chat.orEmpty().toJson()}&user=${viewModel.state.user.toJson()}&id=${details._id.toString()}"))
        }, text = PresentationText.Dynamic("Чат с экспертом"))

        Spacer(modifier = Modifier.height(SpaceNormal))

        if(details.state.contentEquals("new")) {
            BigButton(onClick = {
                viewModel.onUpdate()
                navController.popBackStack()
            }, text = PresentationText.Dynamic("Вернуть в доработку"),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)

            )
        }

    }
}