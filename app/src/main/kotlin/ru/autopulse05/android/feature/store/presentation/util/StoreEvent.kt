package ru.autopulse05.android.feature.store.presentation.util

sealed class StoreEvent {
  object CallSupport : StoreEvent()
  object WriteToSupport : StoreEvent()
  data class MenuItemClick(val value: MenuItems) : StoreEvent()
}
