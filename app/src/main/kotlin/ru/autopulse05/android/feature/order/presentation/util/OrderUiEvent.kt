package ru.autopulse05.android.feature.order.presentation.util

sealed class OrderUiEvent {
  data class Toast(val text: String) : OrderUiEvent()
  object Success : OrderUiEvent()
}
