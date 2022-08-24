package ru.autopulse05.android.feature.info.presentation.refunds

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
import ru.autopulse05.android.shared.presentation.components.MarkedList
import ru.autopulse05.android.shared.presentation.components.MarkedListItem
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun RefundsScreen() {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .padding(SpaceNormal)
      .verticalScroll(scrollState)
  ) {
    Text(
      text = PresentationText.Resource(R.string.refunds).asString(),
      style = MaterialTheme.typography.h1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Условия возврата").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Гарантия на оригинальные автозапчасти распространяется только в случае установки на сервисной станции у официального дилера соответствующего производителя.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Возврат товара надлежащего качества").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Обязательное условие возврата — сохранность заводской упаковки, комплектации и товарного вида изделия.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Возврат товара возможен только в надлежащем качестве и товарном виде: отсутствие следов масел, смазки, топлива, следов установки или сопряжения с другими деталями в целой и чистой упаковке, сохранены потребительские свойства, пломбы, фабричные ярлыки. В случае не выполнения данного требования, возврат принят не будет.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Товар может быть принят без удержания комиссии только в следующих случаях:")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    MarkedList(
      items = listOf(
        PresentationText.Dynamic("неправильно выписали (клиент получил товар, которого не было в заказе);"),
        PresentationText.Dynamic("неправильно отгрузили (клиент получил товар, которого нет в документе);"),
        PresentationText.Dynamic("неверно проинформировали;"),
        PresentationText.Dynamic("брак;"),
        PresentationText.Dynamic("некомплект.")
      )
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Обратите внимание! При заявке на возврат с пометкой \"не подошло к а/м\" необходимо предоставить полные данные по а/м: модель, VIN-код, модель двигателя, дата выпуска.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Мы работаем для вас — все рекламации разбираются индивидуально.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Возврат товара ненадлежащего качества").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Внимание! Наша компания отвечает за качество проданного товара и готова возместить стоимость дефектного товара в течение гарантийного срока.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Бракованный товар принимается без комиссии.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Для возврата товара ненадлежащего качества, относящегося к электрике, наряду с общепринятыми правилами возврата, требуется так же предоставить письменное заключение специалиста (обязательна сертификация на право проведения данной диагностики и выдачи заключений), после проведения диагностики.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Соглашения по условиям возврата товаров").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("ВАЖНО: для рассмотрения заявки на обратный выкуп необходимо предоставить ФОТО товара и упаковки с маркировкой!")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Обратный выкуп в течение 7 дней от даты доставки – без уценки.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Обратный выкуп в течение 8-14 дней – уценка 10%.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Обратный выкуп более 15 дней от даты доставки – не производится.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Должно быть выполнено одно из условий:").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("- Менее 1,5% от средней за три месяца суммы закупок")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("- Или до 4 возвратов в месяц").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("НЕ подлежат обратному выкупу категории товаров:").asString(),
      style = MaterialTheme.typography.h3
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("1) Масла и смазочные материалы, жидкости и антифризы.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("2) Автомобильные химические средства (автохимия и автокосметика).")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("3) Детали электрики (Автомобильные лампы, Датчики и электронные блоки управления, Высоковольтные провода, Свечи зажигания и накала, прочее электрооборудование).")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("4) Аккумуляторные батареи.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("5) Компоненты стеклоочистителя (щетки, резинки).")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("6) Элементы кузова (двери, капоты, панели крыш, крышки багажника, крылья, бампера и большие конструктивные элементы.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("7) Элементы остекления кузова (лобовые, задние, боковые стекла, стекла люков.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Если деталь НЕ устанавливалась и не была в эксплуатации:")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    MarkedListItem(text = PresentationText.Dynamic("заполненный Акт рекламации."))

    Text(
      text = PresentationText.Dynamic("Если деталь устанавливалась и была в эксплуатации:")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    MarkedList(
      items = listOf(
        PresentationText.Dynamic("Заполненный Акт рекламации;"),
        PresentationText.Dynamic("Копию наряд-заказа сервиса, где производилась установка товара на автомобиль;"),
        PresentationText.Dynamic("Копию наряд-заказа сервиса, где производился демонтаж товара с автомобиля;"),
        PresentationText.Dynamic("Акт выполненных работ, где производились работы.")
      )
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Если возврат товара поступает без указанного пакета документов, то по истечение 3-х дней заявленный бракованный товар будет возвращен обратно клиенту с пометкой «Отсутствует необходимый пакет документов» в Акте возврата. В этом случае возможность повторной заявки будет отсутствовать.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("2.3 На отдельные виды товаров устанавливаются особый порядок и условия приема и рассмотрения рекламаций, а именно:")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    MarkedList(
      items = listOf(
        PresentationText.Dynamic("Детали электрической группы перед установкой должны быть протестированы. После эксплуатации на автомобиле детали электрической группы к возврату/рекламации не принимаются. (лямбда-зонды, свечи, лампы, коммутаторы);"),
        PresentationText.Dynamic("На детали, требующие профессиональной установки и соответствующей специализации (детали двигателя, в том числе сцепления, тормозной и топливной системы, детали электрической группы, насосы гидроусилителя руля, рулевые рейки, сцепление, компрессоры кондиционера) претензии по браку рассматриваются только в том случае, если их установка производилась в специализированном сервисном центре по ремонту и обслуживанию автомобилей данной марки и производителя, что подтверждено соответствующими сертификатами. Копия документов должна быть приложена к акту рекламации.")
      )
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("2.4 Поставщик не несет гарантийных обязательств, а покупатель не вправе требовать их выполнения в случаях:")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    MarkedList(
      items = listOf(
        PresentationText.Dynamic("ремонта или замены Товара (отдельных его частей) покупателем или третьими лицами, не имеющими необходимой квалификации;"),
        PresentationText.Dynamic("неправильной эксплуатации, не проведения или несвоевременного проведения обслуживания Товара в сертифицированном центре;"),
        PresentationText.Dynamic("использование расходных материалов, не соответствующих требованиям производителя;"),
        PresentationText.Dynamic("переделки Товара или его частей;"),
        PresentationText.Dynamic("действий третьих лиц, обстоятельств непреодолимой силы или их последствий, а также в случаях иных недостатков, возникших не по вине Поставщика.")
      )
    )


    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Раздел 3. Условия возврата аккумуляторных батарей")
        .asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Гарантия на аккумуляторные батареи предоставляется сроком на 12 (двенадцать) месяцев с даты отпускной накладной. Продавец гарантирует надёжное качество и работоспособность АКБ при строгом соблюдении инструкции по эксплуатации.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Гарантия на АКБ не предоставляется в следующих случаях:")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    MarkedList(
      items = listOf(
        PresentationText.Dynamic("в случае отсутствия/повреждения заводской маркировки;"),
        PresentationText.Dynamic("в случае обнаружения механических повреждений;"),
        PresentationText.Dynamic("в случае самостоятельного ремонта батареи;"),
        PresentationText.Dynamic("в случае предъявления батареи, разряженной до состояния 10 В и ниже (без нагрузки). Исключая случаи связанные с заводским дефектом АКБ (внутренний обрыв цепи);"),
        PresentationText.Dynamic("в случае выявления неисправности электрооборудования автомобиля (черный или красно-бурый электролит, пониженный уровень электролита, налёт на внутренних поверхностях АКБ, с разрушением или без корпуса батареи);"),
        PresentationText.Dynamic("в случае использования АКБ не по прямому назначению;"),
        PresentationText.Dynamic("при обнаружении разности плотности электролита в банках более чем на 0,02 г/см³.")
      )
    )


    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("ОТКАЗ РАБОТЫ АКБ ПО ПРИЧИНЕ ГЛУБОКОГО РАЗРЯДА НЕ МОЖЕТ БЫТЬ ОСНОВАНИЕМ ДЛЯ ВОЗВРАТА!")
        .asString(),
      style = MaterialTheme.typography.h3
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("При несоблюдении любого из условий Разделов 1-3 претензии по качеству Товара не принимаются!")
        .asString(),
      style = MaterialTheme.typography.body1
    )
  }
}

@Preview(
  name = "Refunds Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun RefundsScreenPreview() {
  RefundsScreen()
}