package ru.autopulse05.android.shared.domain.use_case

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import ru.autopulse05.android.shared.domain.util.Data

class DialToUseCase(
  private val application: Application
) {
  private val context: Context get() = application.applicationContext

  operator fun invoke(phone: String): Data<Unit> = try {
    val callIntent = Intent(Intent.ACTION_DIAL).apply {
      data = Uri.parse("tel:$phone")
      flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    ContextCompat.startActivity(context, callIntent, null)
    Data.Success(value = Unit)
  } catch (e: Exception) {
    Data.Error(message = e.message)
  }
}