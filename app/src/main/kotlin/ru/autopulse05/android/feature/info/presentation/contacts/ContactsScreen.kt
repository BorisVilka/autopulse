package ru.autopulse05.android.feature.info.presentation.contacts

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.components.TableCell
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ContactsScreen() {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .padding(SpaceNormal)
      .verticalScroll(scrollState)
  ) {
    Text(
      text = PresentationText.Resource(R.string.contacts).asString(),
      style = MaterialTheme.typography.h1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxWidth()
    ) {
      val column1Weight = .40f
      val column2Weight = .60f

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Resource(id = R.string.company_address, append = ":"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic(
            "Дагестан\n" +
                "Махачкала ул Сулеймана, Стальского 58"
          ),
          weight = column2Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Resource(id = R.string.company_telephone, append = ":"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic(
            "+7 928-049-25-04\n" +
                "+7 922-398-1-555"
          ),
          weight = column2Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Resource(id = R.string.company_working_mode, append = ":"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic("Пн-Вс с 9:00 до 19:00"),
          weight = column2Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Resource(id = R.string.company_email, append = ":"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic(" autopulse05@yandex.ru"),
          weight = column2Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Resource(id = R.string.company_site, append = ":"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic("http://autopulse05.ru"),
          weight = column2Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Resource(id = R.string.company_name, append = ":"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic(
            "" +
                "ИП\n" +
                "Сулейманов\n" +
                "Мусаиб Насирович."
          ),
          weight = column2Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Resource(id = R.string.company_entity_address, append = ":"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic(
            "РД.368783\n" +
                "Магарамкентский р-н\n" +
                "с Джепель ул Лезгинцева."
          ),
          weight = column2Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Resource(id = R.string.company_inn, append = ":"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic("052301663600"),
          weight = column2Weight
        )
      }
    }
  }
}

@Preview(
  name = "Contacts Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ContactsScreenPreview() {
  ContactsScreen()
}