package ru.autopulse05.android.feature.user.presentation.profile.components

import android.content.res.Configuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.feature.vin.domain.model.GuestInfo
import ru.autopulse05.android.feature.vin.domain.model.VinInfo

@Composable
fun VinRequestItem(
  vinRequest: VinInfo,
) {

  Text(text = vinRequest.id)

  Text(text = vinRequest.resellerId)

  Text(text = vinRequest.siteId)

  Text(text = vinRequest.clientId)

  Text(text = vinRequest.guestInfo.name)

  Text(text = vinRequest.guestInfo.phone)

  Text(text = vinRequest.guestInfo.email)
}

@Preview(
  name = "VinRequest Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun VinRequestItemPreview() {
  VinRequestItem(
    vinRequest = VinInfo(
      id = "id",
      resellerId = "resellerId",
      siteId = "siteId",
      clientId = "clientId",
      guestInfo = GuestInfo(
        name = "name",
        phone = "phone",
        email = "email",
      )
    )
  )
}