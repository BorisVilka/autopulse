package ru.autopulse05.android.feature.user.presentation.profile.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.hilt.navigation.compose.hiltViewModel
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.user.presentation.profile.ProfileViewModel
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ProfileTabPagePayments(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Column(modifier = Modifier
        .background(color = MaterialTheme.colors.surface)
    ) {
        state.payments.forEach {
            Column(modifier = Modifier.shadow(elevation = SpaceSmall)
                .padding(SpaceNormal)
                .background(color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small)
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