package ru.autopulse05.android.feature.vin.presentation.detail

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.autopulse05.android.feature.car.domain.use_case.CarUseCases
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.feature.vin.domain.use_case.VinUseCases
import ru.autopulse05.android.feature.vin.presentation.detail.util.VinDetailState
import javax.inject.Inject

@HiltViewModel
class VinDetailViewModel @Inject constructor(
    application: Application,
    private val carUseCases: CarUseCases,
    private val vinUseCases: VinUseCases,
    private val userRepository: UserRepository
): PreferencesViewModel(application = application) {

    var state by mutableStateOf(VinDetailState())
    var getUserJob: Job? = null
    var onUpdateJob: Job? = null

    private fun getUser() {
        getUserJob?.cancel()

        getUserJob = userRepository
            .get()
            .onEach { entity ->
                Log.d("TAG","FFF ${entity?.id}")
                if(entity!=null) state = state.copy(user = entity)
            }
            .launchIn(viewModelScope)
    }

    fun onUpdate() {
        onUpdateJob?.cancel()
        onUpdateJob = vinUseCases.update(
            accessHash = preferencesState.accessHash,
            siteHash = preferencesState.siteHash,
            id = state.id!!
        ).onEach {
            if(it.status!=null) {

            }
        }.launchIn(viewModelScope)
    }

    init {
        getPreferences()
        getUser()
    }
}