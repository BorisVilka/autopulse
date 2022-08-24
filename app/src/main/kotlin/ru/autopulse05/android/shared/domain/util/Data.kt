package ru.autopulse05.android.shared.domain.util

sealed class Data<T>(val message: String? = null) {
  class Success<T>(val value: T) : Data<T>()
  class Error<T>(message: String? = null, val code: Int? = null) : Data<T>(message = message)
  class Loading<T>: Data<T>()
}