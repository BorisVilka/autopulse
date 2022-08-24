package ru.autopulse05.android.feature.user.presentation.sign_in.util

import ru.autopulse05.android.shared.presentation.util.FormTextFieldData

data class SignInFormState(
  val hasError: Boolean = false,
  val login: FormTextFieldData = FormTextFieldData(),
  val password: FormTextFieldData = FormTextFieldData()
)