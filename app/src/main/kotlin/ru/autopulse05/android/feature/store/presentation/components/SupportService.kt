package ru.autopulse05.android.feature.store.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun SupportService(
  modifier: Modifier = Modifier,
  onCallClick: () -> Unit,
  onWriteClick: () -> Unit
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
  ) {
    Text(
      text = PresentationText.Resource(R.string.support_service).asString(),
      style = MaterialTheme.typography.h1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(text = PresentationText.Resource(R.string.we_answer_your_questions).asString())

    Spacer(modifier = Modifier.height(SpaceNormal))

    SupportServiceButtons(
      onCallClick = onCallClick,
      onWriteClick = onWriteClick
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    Text(
      text = PresentationText.Resource(R.string.if_you_leave_a_request_at_night).asString(),
      textAlign = TextAlign.Center
    )
  }
}

@Preview(
  name = "Support Service",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SupportServicePreview() {
  SupportService(
    onCallClick = {},
    onWriteClick = {}
  )
}