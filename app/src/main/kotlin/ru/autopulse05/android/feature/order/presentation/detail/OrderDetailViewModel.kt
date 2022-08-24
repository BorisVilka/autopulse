package ru.autopulse05.android.feature.order.presentation.detail

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.autopulse05.android.feature.order.presentation.detail.util.OrderDetailEvent
import ru.autopulse05.android.feature.order.presentation.detail.util.OrderDetailState
import ru.autopulse05.android.feature.order.presentation.util.OrderEvent
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import javax.inject.Inject


@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    application: Application,

) : PreferencesViewModel(application = application) {

    var state by mutableStateOf(OrderDetailState())


    fun onEvent(event: OrderDetailEvent): Unit = when(event) {
        is OrderDetailEvent.PositionsVisibilityChange -> state = state.copy(
            isPositionsVisible = event.value
        )
        is OrderDetailEvent.InitialValuesChange -> state = state.copy(
            positions = event.positions,
        )
    }
}