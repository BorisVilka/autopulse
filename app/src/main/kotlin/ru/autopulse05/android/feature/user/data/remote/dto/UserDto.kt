package ru.autopulse05.android.feature.user.data.remote.dto

import ru.autopulse05.android.feature.user.domain.model.User


data class UserDto(
  val id: String,
  val code: String,
  val email: String,
  val name: String,
  val mobile: String,
  val organization: String
)

fun UserDto.toUser() =
  User(
    id = id,
    code = code,
    email = email,
    name = name,
    mobile = mobile,
    organization = organization
  )