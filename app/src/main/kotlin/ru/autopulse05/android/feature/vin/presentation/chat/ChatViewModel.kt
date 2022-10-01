package ru.autopulse05.android.feature.vin.presentation.chat

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
import ru.autopulse05.android.feature.vin.data.remote.dto.ChatDto
import ru.autopulse05.android.feature.vin.domain.use_case.VinUseCases
import ru.autopulse05.android.feature.vin.presentation.chat.util.ChatState
import ru.autopulse05.android.feature.vin.presentation.list.util.VinListState
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    application: Application,
    private val carUseCases: CarUseCases,
    private val vinUseCases: VinUseCases,
    private val userRepository: UserRepository,

): PreferencesViewModel(application = application)   {

    var state by mutableStateOf(ChatState())
    var getUserJob: Job? = null
    var getChatJob: Job? = null
    var messageJob: Job? = null

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


    fun updateChat() {
        try {
            getChatJob?.cancel()
            getChatJob = vinUseCases.chat(
                siteHash = preferencesState.siteHash,
                accessHash = preferencesState.accessHash,
                queryId = state.id
            ).onEach {
                Log.d("TAG","${it.size} ${state.chat.size}")
                val tmp = mutableListOf<ChatDto>()
                tmp.addAll(state.chat)
                for(i in it) if(!tmp.contains(i)) tmp.add(i)
                tmp.sortBy { it.timestamp }
                Log.d("TAG","${tmp.size}")
                state.chat.clear()
                state.chat.addAll(tmp)
            }.launchIn(viewModelScope)
        } catch (e: Exception) {

        }
    }

    fun sendMessage(message: String) {
        messageJob?.cancel()
        messageJob = vinUseCases.message(
            siteHash = preferencesState.siteHash,
            accessHash = preferencesState.accessHash,
            user = state.user!!.id,
            id = state.id,
            message = message
        ).onEach { data ->
            when(data) {
                is Data.Success -> {
                    state = state.copy(message = "")
                }
            }
        }.launchIn(viewModelScope)
    }

    init {
        getPreferences()
        getUser()
    }

}