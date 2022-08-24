package ru.autopulse05.android.feature.cooperation.presentation

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
import com.skydoves.landscapist.glide.GlideImage
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.SpaceExtraSmall
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun CooperationScreen() {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .padding(SpaceNormal)
      .verticalScroll(scrollState),
  ) {
    Text(
      text = PresentationText.Resource(R.string.cooperation).asString(),
      style = MaterialTheme.typography.h1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    GlideImage(
      imageModel = "https://f.nodacdn.net/418184"
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Приглашаем к сотрудничеству индивидуальных предпринимателей, юр.лиц, автосервисы, а также частных мастеров").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Мы рады предложить Вам низкие цены и широкий выбор условий поставок. Ваши запросы обрабатываются в первую очередь и в любом удобном для вас виде: по телефону, почте, через, whats app и т.д..").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Для данной категории покупателей по Вологде осуществляется доставка, в регионы - регулярные отгрузки транспортными компаниями. Транспортные компании и регулярность отгрузок подбираются с учетом пожеланий клиента.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Для автосервисов мы можем предложить услугу: \"Внештатный снабженец\"").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Вам не нужно будет заключать договора со множеством поставщиков и тратить время на подбор запчастей, вам не нужно будет платить зарплату и премию штатному сотруднику. Мы можем подобрать запчасти для вас. Вы делаете нам запрос, мы привозим запчасти. Ответственность за подбор несем тоже мы. Подробнее по телефону +7928 87-83-999.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Что получаете вы:").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("1. Личный кабинет для размещения и отслеживания заказов, ведения финансовых расчетов и др.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("2. Договорную скидку, учитывающую объем закупок в месяц.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("3. Оригинальные и неоригинальные каталоги по подбору автозапчастей, масел и аксессуаров.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("4. Профессиональное консультирование и подбор запчастей нашими менеджерами.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("5. Доставку заказов по Вологде и отгрузку в ТК для других регионов.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Возможность размещения вашего наличия у нас на сайте.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Основные условия работы:").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("1. Ответственный подход к заказу и подбору автозапчастей.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("2. Своевременная оплата заказов.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("3. Необходимо пройти регистрацию на сайте.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("По всем дополнительным вопросам звоните по телефону +7928 87-83-999. Мы в оперативном порядке ответим на все интересующие вопросы.").asString(),
      style = MaterialTheme.typography.body1
    )
  }
}

@Preview(
  name = "Cooperation Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CooperationScreenPreview() {
  CooperationScreen()
}