package ru.autopulse05.android.feature.store.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceLarge
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun PagerItem(
    title: String = "",
    desc: String = "",
    leftClick: () -> Unit =  {},
    rightClick: () -> Unit = {}
) {

    Box(modifier = Modifier.fillMaxSize()
        ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(SpaceNormal)) {

            Text(text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = Color.Red)

            Spacer(modifier = Modifier.height(SpaceNormal))

            Text(text = desc,)
        }


    }
}