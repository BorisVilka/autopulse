package ru.autopulse05.android.feature.company.presentation.util

import ru.autopulse05.android.shared.presentation.util.Screens

sealed class CompanyScreens(
  name: String
) : Screens(
  prefix = PREFIX,
  name = name
) {
  companion object {
    const val PREFIX = "company"
  }

  object About : CompanyScreens("about")
  object Conditions : CompanyScreens("conditions")
  object Confidentiality : CompanyScreens("confidentiality")
  object Refunds : CompanyScreens("refunds")
}