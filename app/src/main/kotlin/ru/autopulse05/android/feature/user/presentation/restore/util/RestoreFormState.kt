package ru.autopulse05.android.feature.user.presentation.restore.util

import ru.autopulse05.android.shared.presentation.util.FormTextFieldData

data class RestoreFormState(
  val hasError: Boolean = false,
  val emailOrMobile: FormTextFieldData = FormTextFieldData(
    value = ""
  )
)