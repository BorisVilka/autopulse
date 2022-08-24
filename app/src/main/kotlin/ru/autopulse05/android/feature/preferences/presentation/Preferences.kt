package ru.autopulse05.android.feature.preferences.presentation

import kotlinx.serialization.Serializable

@Suppress("SpellCheckingInspection")
@Serializable
data class Preferences(
  val login: String = "",
  val passwordHash: String = "",
  val locale: String = "ru_RU",
  val adminLogin: String = "api@id13966",
  val adminPasswordHash: String = "9e362efd7db119663fe462b22989ddcd",
  val laximoLogin: String = "ru817973",
  val laximoPassword: String = "nrTrv6171nwlXya",
  val siteHash: String = "24864b2097d22fe79f303b9d6933dc7205ca086f",
  val accessHash: String = "91a98b71c2378449ad7bd376bf28b2ee",
  val clientId: String = "",
  val supportPhone: String = "+79288783999",
  val supportEmail: String = "autopulse05@yandex.ru"
) {
  companion object {
    const val LOCATION = "preferences.json"
  }

  val isLoggedIn: Boolean get() = login.isNotEmpty() && passwordHash.isNotEmpty()
}
