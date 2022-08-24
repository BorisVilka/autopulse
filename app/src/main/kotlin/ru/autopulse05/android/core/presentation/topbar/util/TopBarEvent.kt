package ru.autopulse05.android.core.presentation.topbar.util

sealed class TopBarEvent {
  object OpenDrawer : TopBarEvent()
  object ProfileClick : TopBarEvent()
  object Search : TopBarEvent()
}