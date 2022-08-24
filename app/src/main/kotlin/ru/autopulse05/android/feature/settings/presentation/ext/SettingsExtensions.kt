package ru.autopulse05.android.feature.settings.presentation.ext

import android.content.Context
import androidx.datastore.dataStore
import ru.autopulse05.android.feature.settings.data.source.SettingsSerializer

const val SETTINGS_FILE = "app-settings.json"

val Context.dataStore by dataStore(SETTINGS_FILE, SettingsSerializer)