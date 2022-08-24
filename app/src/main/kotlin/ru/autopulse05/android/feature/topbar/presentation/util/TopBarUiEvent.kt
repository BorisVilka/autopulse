package ru.autopulse05.android.feature.topbar.presentation.util

sealed class TopBarUiEvent {
  object OpenDrawerClicked : TopBarUiEvent()
  object OpenUserMenu: TopBarUiEvent()
  object SignIn: TopBarUiEvent()
}
