package ru.autopulse05.android.feature.user.domain.util

sealed class SignUpMarket(val type: String) {
  object Wholesale: SignUpMarket(type = "Розница")
  object Retail : SignUpMarket(type = "Опт")
}
