package ru.autopulse05.android.feature.payment_and_shipment.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
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
import ru.autopulse05.android.shared.presentation.components.MarkedListItem
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun PaymentAndShipmentScreen() {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .padding(SpaceNormal)
      .verticalScroll(scrollState),
  ) {
    Text(
      text = PresentationText.Resource(R.string.payment_and_shipment).asString(),
      style = MaterialTheme.typography.h1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Компания АВТОПУЛЬС предлагает своим клиентам различные способы оплаты:")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    MarkedListItem(text = PresentationText.Dynamic("наличными при получении товара;"))
    MarkedListItem(text = PresentationText.Dynamic("банковским переводом;"))
    MarkedListItem(text = PresentationText.Dynamic("через шлюз сбербанка."))

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("1. Описание процесса платежа банковской картой")
        .asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Для выбора оплаты товара с помощью банковской карты на соответствующей странице сайта необходимо нажать кнопку «Оплата банковской картой».")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Оплата происходит через авторизационный сервер Процессингового центра Банка с использованием Банковских кредитных карт следующих платежных систем:")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("2. Процесс передачи данных").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Для оплаты покупки Вы будете перенаправлены на платежный шлюз ПАО \"Сбербанк России\" для ввода реквизитов Вашей карты. Пожалуйста, приготовьте Вашу пластиковую карту заранее. Соединение с платежным шлюзом и передача информации осуществляется в защищенном режиме с использованием протокола шифрования SSL.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("В случае если Ваш банк поддерживает технологию безопасного проведения интернет-платежей Verified By Visa или MasterCard Secure Code для проведения платежа также может потребоваться ввод специального пароля. Способы и возможность получения паролей для совершения интернет-платежей Вы можете уточнить в банке, выпустившем карту.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Настоящий сайт поддерживает 256-битное шифрование. Конфиденциальность сообщаемой персональной информации обеспечивается ПАО \"Сбербанк России\". Введенная информация не будет предоставлена третьим лицам за исключением случаев, предусмотренных законодательством РФ. Проведение платежей по банковским картам осуществляется в строгом соответствии с требованиями платежных систем Visa Int. и MasterCard Europe Sprl.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("3. Процесс оплаты").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Оплата банковскими картами осуществляется после проверки заказа менеджером интернет-магазина. На условии 100% предоплаты.")
        .asString(),
      style = MaterialTheme.typography.h3
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("При выборе формы оплаты с помощью пластиковой карты проведение платежа по заказу производится непосредственно после его оформления. После завершения оформления заказа в нашем магазине, Вы должны будете нажать на кнопку «Оплата банковской картой», при этом система переключит Вас на страницу авторизационного сервера, где Вам будет предложено ввести данные пластиковой карты, инициировать ее авторизацию, после чего вернуться в наш магазин кнопкой \"Вернуться в магазин\". После того, как Вы возвращаетесь в наш магазин, система уведомит Вас о результатах авторизации. В случае подтверждения авторизации Ваш заказ будет автоматически выполняться в соответствии с заданными Вами условиями. В случае отказа в авторизации карты Вы сможете повторить процедуру оплаты.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Оплатить банковской картой Вы можете:").asString(),
      style = MaterialTheme.typography.body1
    )

    MarkedListItem(text = PresentationText.Dynamic("в розничных магазинах, в пунктах интернет-магазина;"))
    MarkedListItem(text = PresentationText.Dynamic("на сайте."))

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("После оплаты Вы получите все необходимые документы.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Оплата производится только в российских рублях.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("4. В случае отказа клиентом от товара/услуги (аннулировании заказа)")
        .asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("При аннулировании позиций из оплаченного заказа (или при аннулировании заказа целиком) Вы можете заказать другой товар на эту сумму, либо вернуть всю сумму на карту предварительно написав письмо на e-mail.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("5. Доставка и выдача заказа, оплаченного пластикатовой картой")
        .asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Доставка или выдача при самовывозе товара, оплаченного пластиковой картой, осуществляется со дня зачисления денег на наш счет.Согласно сроков указанных на сайте.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Частные покупатели для получения товара должны предъявить паспорт владельца пластиковой карты, по которой производилась оплата заказа. Представитель юридического лица должен иметь доверенность с печатью от компании-плательщика или саму печать.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("СДЭК").asString(),
      style = MaterialTheme.typography.h2
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    GlideImage(
      imageModel = "https://yurbel.ru/ckeditor_assets/pictures/183/content_CDEK.png",
      modifier = Modifier.fillMaxWidth(0.5F)
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Доставка до Пунктов Выдачи Заказов «СДЭК» в течении 2-6 дней. При сумме заказа от 6 000 руб — бесплатно.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Почтой России").asString(),
      style = MaterialTheme.typography.h2
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    GlideImage(
      imageModel = "https://yurbel.ru/ckeditor_assets/pictures/181/content_pochta-rossii.png",
      modifier = Modifier.fillMaxWidth(0.5F)
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Доставка стоимость доставки «Почты России» 450 руб. При сумме заказа от 6 000 руб — бесплатно.")
        .asString(),
      style = MaterialTheme.typography.body1
    )
  }
}


@Preview(
  name = "Payment And Shipment Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PaymentAndDeliveryScreenPreview() {
  PaymentAndShipmentScreen()
}
