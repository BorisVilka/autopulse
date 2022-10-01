package ru.autopulse05.android.feature.vin.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.user.domain.model.User
import ru.autopulse05.android.feature.vin.data.remote.dto.ChatDto
import ru.autopulse05.android.shared.presentation.components.InputField
import ru.autopulse05.android.shared.presentation.util.PresentationText
import kotlin.time.Duration.Companion.seconds


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(
    navController: NavController,
    chat: MutableList<ChatDto>,
    user: User,
    queryId: String,
    viewModel: ChatViewModel = hiltViewModel()
)   {
    viewModel.state.user = user
    viewModel.state.id = queryId
    if(viewModel.state.chat.isEmpty()) viewModel.state.chat.addAll(chat)
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        while(true) {
            delay(10.seconds)
            viewModel.updateChat()
        }
    }

    Column(modifier = Modifier.fillMaxSize(),

        ) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth().fillMaxHeight()
            .weight(5f)
            .padding(SpaceSmall)) {
            items(viewModel.state.chat.size) { ind ->
                val it = viewModel.state.chat[ind]
                val gravity = if(user.id.contentEquals(it.userId)) Arrangement.End else Arrangement.Start
                val color = (if(user.id.contentEquals(it.userId)) Color.Cyan else Color.Gray).copy(alpha = 0.5f)
                Row(modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = gravity) {
                    Card(
                        backgroundColor = color,
                    ) {
                        Text(text = it.comment,
                            color = Color.White,
                        modifier = Modifier.padding(SpaceSmall)
                            )
                    }
                }
                Spacer(modifier = Modifier.height(SpaceNormal))
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .height(100.dp),
        ) {
            Box {
                BasicTextField(
                    value = viewModel.state.message,
                    onValueChange = {
                        viewModel.state = viewModel.state.copy(
                            message = it
                        ) },
                    singleLine = false,
                    textStyle = TextStyle(color = MaterialTheme.colors.onSecondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity))
                        .padding(SpaceNormal),
                    keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {keyboardController?.hide()})
                )

                if (viewModel.state.message.isEmpty()) {
                    Text(
                        text = "Введите сообщение",
                        modifier = Modifier.padding(SpaceNormal),
                        color = MaterialTheme.colors.onSecondary
                    )
                }
            }
            Spacer(modifier = Modifier.width(SpaceSmall))
            IconButton(onClick = {
                if(viewModel.state.message.isEmpty()) return@IconButton
                viewModel.sendMessage(viewModel.state.message)
                keyboardController?.hide()
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_send_24),
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                        .align(Alignment.CenterEnd),
                    contentDescription = PresentationText.Resource(id = R.string.profile).asString(),
                    tint = Color.BrandYellow
                )
            }
        }
    }
}