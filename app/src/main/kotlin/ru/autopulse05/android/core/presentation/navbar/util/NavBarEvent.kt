package ru.autopulse05.android.core.presentation.navbar.util

sealed class NavBarEvent {
  data class OnNavBarItemClick(val value: NavBarItems) : NavBarEvent()
}