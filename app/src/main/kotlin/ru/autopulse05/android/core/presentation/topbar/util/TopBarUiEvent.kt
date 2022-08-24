package ru.autopulse05.android.core.presentation.topbar.util

sealed class TopBarUiEvent {
  object OpenDrawer : TopBarUiEvent()
  object OpenUserMenu: TopBarUiEvent()
  object SignIn: TopBarUiEvent()
  object Search: TopBarUiEvent()
}
