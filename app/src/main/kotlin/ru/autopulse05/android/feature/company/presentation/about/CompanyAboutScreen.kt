package ru.autopulse05.android.feature.company.presentation.about

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceExtraSmall
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.components.MarkedListItem
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun CompanyAboutScreen() {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .padding(SpaceNormal)
      .verticalScroll(scrollState)
  ) {
    Text(
      text = PresentationText.Resource(R.string.about_company).asString(),
      style = MaterialTheme.typography.h1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Группа компаний «АВТОПУЛЬС» работает в Южном Дагестане уже более 8 лет (основана в 2010г.).").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("А начиналось все в 2010 г. с розничной торговли товарами повседневного спроса. Затем был открыт магазин запчастей для иномарок.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Благодаря сильной команде и динамичному развитию, сегодня «Автопульс» является одним из ведущих поставщиков автозапчастей.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Наша цель, которой мы остаемся верными и по сей день, это полное удовлетворение спроса на запчасти для автомобилей иностранного и российского производства.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Компания «Автопульс» – предлагаем поставку автозапчастей для автомобилей иностранного производства в кратчайшие сроки. Работаем в режиме экспресс-заказа.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Программное обеспечение").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Text(
      text = PresentationText.Dynamic("Технический отдел компании предлагает клиентам программу с системой поиска и заказа, содержащую прайс-листы всех основных производителей и складов.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Возможности OnLine системы:").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    MarkedListItem(text = PresentationText.Dynamic("ежедневное обновление предложений;"))
    MarkedListItem(text = PresentationText.Dynamic("расширенный поиск из предложений;"))
    MarkedListItem(text = PresentationText.Dynamic("кроссировка при подборе деталей;"))
    MarkedListItem(text = PresentationText.Dynamic("оперативная информация о движении заказа;"))
    MarkedListItem(text = PresentationText.Dynamic("работа с документами;"))
    MarkedListItem(text = PresentationText.Dynamic("каталоги для подбора деталей."))
  }
}

@Preview(
  name = "Company About Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CompanyAboutScreenPreview() {
  CompanyAboutScreen()
}