package ru.autopulse05.android.shared.presentation

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
  protected val context get() = getApplication<Application>()

  protected fun stringResource(@StringRes id: Int): String = context.getString(id)
}