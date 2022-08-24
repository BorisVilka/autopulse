package ru.autopulse05.android.shared.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun RowScope.TableCell(
  weight: Float,
  modifier: Modifier = Modifier,
  content: @Composable ColumnScope.() -> Unit
) {
  Column(modifier = modifier.weight(weight)) {
    content()
  }
}

@Composable
fun RowScope.TableCell(
  text: PresentationText,
  weight: Float,
  modifier: Modifier = Modifier
) {
  Text(
    text = text.asString(),
    modifier
      .weight(weight)
      .padding(SpaceSmall)
  )
}