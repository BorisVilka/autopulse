package ru.autopulse05.android.feature.user.data.remote.dto

data class AddUserDto(
  val status: Int,
  val userCode: String,
  val activationCode: String?,
  val errorMessages: List<String>?
)