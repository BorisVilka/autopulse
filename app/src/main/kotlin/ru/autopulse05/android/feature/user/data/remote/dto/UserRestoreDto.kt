package ru.autopulse05.android.feature.user.data.remote.dto

data class UserRestoreDto(
  val errorCode: String?,
  val errorMessage: String?,
  val status: String?,
  val message: String?
)
