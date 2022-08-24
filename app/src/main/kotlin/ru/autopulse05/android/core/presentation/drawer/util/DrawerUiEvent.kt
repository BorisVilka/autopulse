package ru.autopulse05.android.core.presentation.drawer.util

sealed class DrawerUiEvent {
  data class OnLinkItemClick(val value: DrawerItems.LinkItem) : DrawerUiEvent()
}
