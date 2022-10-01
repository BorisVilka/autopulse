package ru.autopulse05.android.feature.order.presentation.detail

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
import ru.autopulse05.android.feature.cart.domain.use_case.CartUseCases
import ru.autopulse05.android.feature.order.domain.model.Order
import ru.autopulse05.android.feature.order.domain.use_case.OrderObject
import ru.autopulse05.android.feature.order.domain.use_case.OrderUseCases
import ru.autopulse05.android.feature.order.presentation.OrderViewModel
import ru.autopulse05.android.feature.order.presentation.detail.util.OrderDetailEvent
import ru.autopulse05.android.feature.order.presentation.detail.util.OrderDetailState
import ru.autopulse05.android.feature.order.presentation.util.OrderEvent
import ru.autopulse05.android.feature.order.presentation.util.OrderUiEvent
import ru.autopulse05.android.feature.preferences.presentation.Preferences
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject


@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    application: Application,
    private val orderUseCases: OrderUseCases,
    private val userRepository: UserRepository,
) : PreferencesViewModel(application = application) {

    var state by mutableStateOf(OrderDetailState())
    var addPayJob: Job? = null
    private val _uiEventChannel = Channel<OrderUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    fun onEvent(event: OrderDetailEvent): Unit = when(event) {
        is OrderDetailEvent.PositionsVisibilityChange -> state = state.copy(
            isPositionsVisible = event.value
        )
        is OrderDetailEvent.InitialValuesChange -> state = state.copy(
            positions = event.positions,
        )
    }

    init {
        getPreferences()
        getUser()
    }

    override fun onPreferencesChange(preferences: Preferences) {
        super.onPreferencesChange(preferences)

        if (preferences.isLoggedIn) {
            getUser()
        }
    }

    private fun getUser() {
        userRepository
            .get()
            .onEach { entity -> state = state.copy(user = entity) }
            .launchIn(viewModelScope)
    }
    public fun addPay(value: OrderObject, order: Order) {
        addPayJob?.cancel()

        addPayJob = orderUseCases.addPay(
            login = preferencesState.adminLogin,
            passwordHash = preferencesState.adminPasswordHash,
            id = state.user!!.id,
            date = value.date,
            sum = value.sum,
            comment = "Оплата заказа № ${order.number}"
        ).onEach {
                data ->
            when (data) {
                is Data.Error -> {

                    state = state.copy(isLoading = false)
                }
                is Data.Loading -> state = state.copy(isLoading = true)
                is Data.Success -> {
                    _uiEventChannel.send(OrderUiEvent.Success)
                }
            }
        }
            .launchIn(viewModelScope)
    }
}