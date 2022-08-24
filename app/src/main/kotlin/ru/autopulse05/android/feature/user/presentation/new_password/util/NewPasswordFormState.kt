package ru.autopulse05.android.feature.user.presentation.new_password.util

import ru.autopulse05.android.shared.presentation.util.FormTextFieldData

data class NewPasswordFormState(
  val hasError: Boolean = false,
  val phone: FormTextFieldData = FormTextFieldData(),
  val code: FormTextFieldData = FormTextFieldData(),
  val newPassword: FormTextFieldData = FormTextFieldData()
)