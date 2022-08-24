package ru.autopulse05.android.feature.user.presentation.restore.util

sealed class RestoreEvent {
  data class EmailOrMobileChanged(val value: String) : RestoreEvent()
  object Submit : RestoreEvent()
}