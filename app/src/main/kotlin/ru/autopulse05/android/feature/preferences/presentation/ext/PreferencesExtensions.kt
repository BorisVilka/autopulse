package ru.autopulse05.android.feature.preferences.presentation.ext

import android.content.Context
import androidx.datastore.dataStore
import ru.autopulse05.android.feature.preferences.data.source.PreferencesSerializer
import ru.autopulse05.android.feature.preferences.presentation.Preferences

val Context.dataStore by dataStore(Preferences.LOCATION, PreferencesSerializer)