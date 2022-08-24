package ru.autopulse05.android.feature.store.presentation.util

sealed class StoreUiEvent {
  data class MenuItemClick(val value: MenuItems) : StoreUiEvent()
}
