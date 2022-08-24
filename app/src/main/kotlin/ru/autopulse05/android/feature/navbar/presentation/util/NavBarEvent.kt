package ru.autopulse05.android.feature.navbar.presentation.util

sealed class NavBarEvent {
  data class OnNavBarItemClick(val value: NavBarItems) : NavBarEvent()
}