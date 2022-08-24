package ru.autopulse05.android.feature.drawer.presentation.util

sealed class DrawerUiEvent {
  data class OnLinkItemClick(val value: DrawerItems.LinkItem) : DrawerUiEvent()
}
