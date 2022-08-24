package ru.autopulse05.android.feature.topbar.presentation.util

sealed class TopBarEvent {
  object OpenDrawerClicked : TopBarEvent()
  object ProfileClicked : TopBarEvent()
}