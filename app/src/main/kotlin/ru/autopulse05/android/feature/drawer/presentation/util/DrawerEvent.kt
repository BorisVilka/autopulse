package ru.autopulse05.android.feature.drawer.presentation.util

sealed class DrawerEvent {
  data class OnExpandItemClick(val index: Int) : DrawerEvent()
  data class OnLinkItemClick(val value: DrawerItems.LinkItem) : DrawerEvent()
}