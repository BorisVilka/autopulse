package ru.autopulse05.android.feature.user.presentation.sign_up.util

import ru.autopulse05.android.shared.presentation.util.FormSelectorFieldData
import ru.autopulse05.android.shared.presentation.util.FormTextFieldData

data class SignUpState(
  // Main
  val isLoading: Boolean = false,
  val name: FormTextFieldData = FormTextFieldData(),
  val secondName: FormTextFieldData = FormTextFieldData(),
  val phone: FormTextFieldData = FormTextFieldData(),
  val email: FormTextFieldData = FormTextFieldData(),
  val password: FormTextFieldData = FormTextFieldData(),
  val repeatedPassword: FormTextFieldData = FormTextFieldData(),
  val city: FormTextFieldData = FormTextFieldData(),
  val termsAgreement: Boolean = true,
  val office: FormSelectorFieldData<String> = FormSelectorFieldData(
    items = listOf(
      "Джепель",
      "Магарамкентский р-он",
      "Махачкала, С. Стальского, 58"
    )
  ),

  // Wholesale
  val entityName: FormTextFieldData = FormTextFieldData(),
  val entityAddress: FormTextFieldData = FormTextFieldData(),
  val region: FormSelectorFieldData<String> = FormSelectorFieldData(),
  val surname: FormTextFieldData = FormTextFieldData(),
  val repeatedEmail: FormTextFieldData = FormTextFieldData(),
  val icq: FormTextFieldData = FormTextFieldData(),
  val organisationType: FormSelectorFieldData<String> = FormSelectorFieldData(
    items = listOf(
      "Автопарк",
      "Автосервис",
      "Автосервис + Магазин",
      "Дистрибьютор",
      "Интернет-магазин",
      "Магазин",
      "Другое"
    )
  ),
  val entityType: FormSelectorFieldData<String> = FormSelectorFieldData(
    items = listOf(
      "ООО",
      "ОАО",
      "ЗАО",
      "ТОО",
      "АО",
      "ИП",
      "ПБОЮЛ",
      "ГУП",
    )
  ),
  val organizationAddress: FormTextFieldData = FormTextFieldData(),
  val actualAddress: FormTextFieldData = FormTextFieldData()
)