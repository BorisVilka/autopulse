package ru.autopulse05.android.feature.info.presentation.conditions

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
import ru.autopulse05.android.core.presentation.ui.theme.SpaceExtraSmall
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.components.MarkedList
import ru.autopulse05.android.shared.presentation.components.TableCell
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ConditionsScreen() {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .padding(SpaceNormal)
      .verticalScroll(scrollState)
  ) {
    Text(
      text = PresentationText.Resource(R.string.conditions).asString(),
      style = MaterialTheme.typography.h1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Условия предоставления скидки").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Скидка устанавливается на 1 месяц. Расчетной величиной для установки скидки является максимальный месячный объем заказов за последние три месяца.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Для Новых клиентов cкидка устанавливается с учетом первоначальной суммы оплаты. При расчете следующих скидок учитывается объем отгрузки за предыдущий месяц.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxWidth()
    ) {
      val column1Weight = .33f
      val column2Weight = .33f
      val column3Weight = .33f

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Dynamic("Наименование"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic("Оборот закупок (руб)"),
          weight = column2Weight
        )
        TableCell(
          text = PresentationText.Dynamic("Скидка от базовой"),
          weight = column3Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Dynamic("«Базовая»"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic(""),
          weight = column2Weight
        )
        TableCell(
          text = PresentationText.Dynamic("0%"),
          weight = column3Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Dynamic("«Разовая закупка»"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic("<30 000"),
          weight = column2Weight
        )
        TableCell(
          text = PresentationText.Dynamic("12%"),
          weight = column3Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Dynamic("Опт «Начальный»"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic(">30 000"),
          weight = column2Weight
        )
        TableCell(
          text = PresentationText.Dynamic("15%"),
          weight = column3Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Dynamic("Опт «Средний»"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic(">100 000"),
          weight = column2Weight
        )
        TableCell(
          text = PresentationText.Dynamic("18%"),
          weight = column3Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Dynamic("Опт «Стандартный»"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic(">300 000"),
          weight = column2Weight
        )
        TableCell(
          text = PresentationText.Dynamic("20%"),
          weight = column3Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Dynamic("Опт «Крупный»"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic(">600 000"),
          weight = column2Weight
        )
        TableCell(
          text = PresentationText.Dynamic("22%"),
          weight = column3Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Dynamic("Опт «Специальный»"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic(">1 000 000"),
          weight = column2Weight
        )
        TableCell(
          text = PresentationText.Dynamic("23%"),
          weight = column3Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Dynamic("VIP"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic(">1 500 000"),
          weight = column2Weight
        )
        TableCell(
          text = PresentationText.Dynamic("24%"),
          weight = column3Weight
        )
      }
    }

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("При первоначальном посещении сайта Вы видите цену для розничным клиентам.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Условия размещения заказов и отгрузки").asString(),
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("Оборот закупок – cумма максимальной закупки (период три месяца).")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxWidth()
    ) {
      val column1Weight = .33f
      val column2Weight = .33f
      val column3Weight = .33f

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Dynamic("Время работы с компанией"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic("Большая сумма отгрузки за расчетный период"),
          weight = column2Weight
        )
        TableCell(
          text = PresentationText.Dynamic("Предоплата при заказе"),
          weight = column3Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Dynamic("Новый клиент, первый заказ"),
          weight = column1Weight
        )
        TableCell(
          text = PresentationText.Dynamic("Не важно"),
          weight = column2Weight
        )
        TableCell(
          text = PresentationText.Dynamic("50%"),
          weight = column3Weight
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TableCell(
          text = PresentationText.Dynamic("Любой период"),
          weight = column1Weight
        )
        TableCell(weight = column2Weight) {
          Text(
            text = PresentationText.Dynamic("До 250 000").asString(),
            modifier = Modifier.padding(SpaceSmall)
          )
          Text(
            text = PresentationText.Dynamic("Свыше 300 000 в течение 1-х месяцев").asString(),
            modifier = Modifier.padding(SpaceSmall)
          )
        }
        TableCell(weight = column3Weight) {
          Text(
            text = PresentationText.Dynamic("25%").asString(),
            modifier = Modifier.padding(SpaceSmall)
          )
          Text(
            text = PresentationText.Dynamic("Без предоплаты").asString(),
            modifier = Modifier.padding(SpaceSmall)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(SpaceSmall))

    MarkedList(
      items = listOf(
        PresentationText.Dynamic("Если ЗАДОЛЖЕННОСТЬ за товар превышает 16% от ОБОРОТА ЗАКУПОК, в независимости от условий размещения заказов, прием заказов ПРИОСТАНАВЛИВАЕТСЯ;"),
        PresentationText.Dynamic("ОТГРУЗКА товара происходит в пределах ПОЛОЖИТЕЛЬНОГО баланса.")
      )
    )


    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("При обороте закупок свыше 1 000 000 рублей возможны ОСОБЫЕ условия заказов и отгрузки. Предоставляется индивидуально.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("По всем вопросам предоставления СКИДОК и УСЛОВИЙ работы:")
        .asString(),
      style = MaterialTheme.typography.h3
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("+7 922-39-81-555  8 (928) 049-25-04  Звонок бесплатный.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("[Авто пульс 05], действующий на основании свидетельства о регистрации № 558844, именуемый в дальнейшем «Продавец» и гражданин [CLIENT], именуемый в дальнейшем «Заказчик», заключили договор о нижеследующем:")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("1. Заказчик заказывает,  а Продавец обязуется доставить авто запчасти (далее Товар) согласно приложению №1 к настоящему договору.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("2. Оплата производится только в рублях.").asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("3. Продавец вправе в одностороннем порядке отказаться от поставки Товара (если, по независящим от Продавца обстоятельствам, заказанный Товар не может быть доставлен), в таком случае Продавец обязан в кратчайший срок  информировать об этом Заказчика и вернуть ему предоплату.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("4. Передача  Товара от  Продавца к Заказчику осуществляется только после полной оплаты стоимости Товара Заказчиком.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("5.Факт приемки товара по качеству и количеству удостоверяется  подписью Заказчика.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("6. Заказчик обязан предоставить продавцу достоверные данные об автомобиле (идентификационный номер автомобиля VIN), для которого приобретается соответствующая запасная часть (Товар). В противном случае Продавец не несет никакой ответственности за соответствие Товара требованиям, которые к нему предъявляет Заказчик.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("7. Стоимость Товара, указанная в приложении №1 к данному договору может быть скорректирована при получении Продавцом Товара, из-за невозможности при принятии заказа определить полную стоимость Товара (в нее не включается стоимость перевозки согласно ее весу или габариту), о чем Продавец обязан предупредить Заказчика заранее, сообщив о приблизительной сумме такой корректировки.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("8. В случае ошибки, допущенной продавцом, при определении необходимой Заказчику детали, ее обмен осуществляется при условии, если указанная деталь не была в употреблении, сохранены товарный вид детали и ее упаковки. Обмен производится в течение 7 календарных дней, при наличии кассового чека.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("9. Претензии Заказчика по возврату (обмену) приобретенного Товара, имеющего скрытые технические дефекты, принимаются в течение 7дней со дня его приобретения, при  условии установки его в авторизованном техническом центре и наличии у покупателя акта экспертизы, выданного авторизованным техническим  центром.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("10. Если Заказчик отказывается от Заказа или получения Товара (при условии, что Товар удовлетворяет всем требованиям, указанным в Приложении №1 к данному договору), то Продавец имеет право потребовать от Заказчика неустойку в размере 30% от стоимости Товара, или  принять Товар у Заказчика на реализацию, с условием выплаты денег после реализации Товара.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("11. Полученный по заказу Товар хранится на складе Продавца в течение 30 дней, затем реализуется . Возврат предоплаты Заказчику осуществляется после продажи Товара.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceExtraSmall))

    Text(
      text = PresentationText.Dynamic("12.Сроки доставки заказа действует от 1 до 4 рабочих дней.")
        .asString(),
      style = MaterialTheme.typography.body1
    )

    Spacer(modifier = Modifier.height(SpaceSmall))

    Text(
      text = PresentationText.Dynamic("Минимальная сумма доставки товара: 1000,00 руб").asString(),
      style = MaterialTheme.typography.h3
    )
  }
}

@Preview(
  name = "Conditions Screen",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ConditionsScreenPreview() {
  ConditionsScreen()
}