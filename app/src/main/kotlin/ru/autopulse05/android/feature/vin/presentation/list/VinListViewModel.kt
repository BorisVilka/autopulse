package ru.autopulse05.android.feature.vin.presentation.list

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.car.domain.use_case.CarUseCases
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.product.presentation.list.util.ProductListUiEvent
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.feature.vin.domain.use_case.VinUseCases
import ru.autopulse05.android.feature.vin.presentation.list.util.VinListState
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject


@HiltViewModel
class VinListViewModel @Inject constructor(
    application: Application,
    private val carUseCases: CarUseCases,
    private val vinUseCases: VinUseCases,
    private val userRepository: UserRepository
): PreferencesViewModel(application = application)  {

    var state by mutableStateOf(VinListState())
    var getListJob: Job? = null
    var getUserJob: Job? = null
    private val _uiEventChannel = Channel<ProductListUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    private fun getUser() {
        getUserJob?.cancel()

        getUserJob = userRepository
            .get()
            .onEach { entity ->
                Log.d("TAG","FFF ${entity?.id}")
                if(entity!=null) state = state.copy(user = entity)
                getList()
            }
            .launchIn(viewModelScope)
    }

    init {
        getPreferences()
        getUser()
    }


    private fun getList() {
        getListJob?.cancel()
        getListJob = vinUseCases.list(
            siteHash = preferencesState.siteHash,
            accessHash = preferencesState.accessHash,
            userId = state.user?.id.orEmpty()
        ).onEach { data ->
            when(data) {
                is Data.Success -> {
                    state = state.copy(
                        list = data.value
                    )
                    //Log.d("TAG",data.value.size.toString()+" |||| "+state.user!!.id)
                }
                is Data.Error -> {
                    _uiEventChannel.send(ProductListUiEvent.Toast(text = "Ошибка, перезагрузите приложение"))
                }
                is Data.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }


}