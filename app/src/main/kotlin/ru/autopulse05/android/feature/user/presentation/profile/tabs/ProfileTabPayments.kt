package ru.autopulse05.android.feature.user.presentation.profile.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.user.presentation.profile.ProfileViewModel
import ru.autopulse05.android.feature.user.presentation.sign_in.util.SignInEvent
import ru.autopulse05.android.shared.presentation.components.InputField
import ru.autopulse05.android.shared.presentation.util.PresentationText
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ProfileTabPagePayments(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Column(modifier = Modifier
        .background(color = MaterialTheme.colors.surface)
    ) {

        Text(text = "Введите дату в формате ДД.ММ.ГГГГ")

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceSmall),
            horizontalArrangement = Arrangement.SpaceAround
            ) {

            BasicTextField(
                value = state.date,
                onValueChange = { value ->
                 viewModel.edit(value)
                },
                singleLine = true,
                modifier = Modifier
                    .background(color = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity))
                    .padding(SpaceNormal)
            )
            Spacer(modifier = Modifier.width(SpaceNormal))
            BasicTextField(
                value = state.dateM,
                onValueChange = { value ->
                    viewModel.editM(value)
                },
                singleLine = true,
                modifier = Modifier
                    .background(color = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity))
                    .padding(SpaceNormal)
            )
        }

        state.payments.filter {
            var it1 = LocalDate.MIN;
            try {
                it1 = LocalDate.from(DateTimeFormatter.ofPattern("dd.MM.yyyy").parse(state.date))
            } catch (e: Exception) {}
            var it3 = LocalDate.MAX;
            try {
                it3 = LocalDate.from(DateTimeFormatter.ofPattern("dd.MM.yyyy").parse(state.dateM))
            } catch (e: Exception) {}
            val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val it2 = LocalDate.from(format.parse(it.createDateTime.subSequence(0,10)))
            return@filter it3.toEpochDay()>=it2.toEpochDay() && it2.toEpochDay()>=it1.toEpochDay();
        }.forEach {
            Column(modifier = Modifier
                .shadow(elevation = SpaceSmall)
                .padding(SpaceNormal)
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = MaterialTheme.shapes.small
                )
                .padding(SpaceNormal)
            ) {

                Text(text = "Платёж от ${it.createDateTime}")

                Spacer(modifier = Modifier.height(SpaceNormal))

                Text(text = "Способ оплаты: ${it.paymentType}")

                Spacer(modifier = Modifier.height(SpaceNormal))

                Text(text = "Номер платежа: ${it.paymentId}")

                Spacer(modifier = Modifier.height(SpaceNormal))

                Text(text = "Сумма: ${it.amount}")

                Spacer(modifier = Modifier.height(SpaceNormal))

                Text(text = "Комментарий: ${it.comment.orEmpty()}")
            }
            Divider()
        }
    }
}