package ru.autopulse05.android.feature.navbar.presentation.util

sealed class NavBarUiEvent {
  data class OnNavBarItemClick(val value: NavBarItems) : NavBarUiEvent()
}
