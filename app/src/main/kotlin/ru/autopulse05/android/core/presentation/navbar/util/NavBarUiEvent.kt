package ru.autopulse05.android.core.presentation.navbar.util

sealed class NavBarUiEvent {
  data class OnNavBarItemClick(val value: NavBarItems) : NavBarUiEvent()
}
