package ru.autopulse05.android.feature.user.presentation.popup.util

import ru.autopulse05.android.feature.user.domain.model.User

data class UserPopupState(
  val user: User? = null,
  val isLoading: Boolean = true
)
