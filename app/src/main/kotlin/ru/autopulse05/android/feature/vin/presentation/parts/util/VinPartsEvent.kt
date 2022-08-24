package ru.autopulse05.android.feature.vin.presentation.parts.util


sealed class VinPartsEvent {
  data class PartsChanged(val value: String) : VinPartsEvent()
  object Next : VinPartsEvent()
}
