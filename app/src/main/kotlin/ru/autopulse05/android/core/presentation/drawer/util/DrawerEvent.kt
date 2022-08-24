package ru.autopulse05.android.core.presentation.drawer.util

sealed class DrawerEvent {
  data class OnExpandItemClick(val index: Int) : DrawerEvent()
  data class OnLinkItemClick(val value: DrawerItems.LinkItem) : DrawerEvent()
}