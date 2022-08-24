package ru.autopulse05.android.feature.drawer.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.feature.drawer.presentation.util.DrawerEvent
import ru.autopulse05.android.feature.drawer.presentation.util.DrawerItems
import ru.autopulse05.android.feature.drawer.presentation.util.DrawerState
import ru.autopulse05.android.feature.drawer.presentation.util.DrawerUiEvent
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor() : ViewModel() {

  private val _uiEventChannel = Channel<DrawerUiEvent>()

  var state by mutableStateOf(DrawerState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun onLinkItemClick(value: DrawerItems.LinkItem) {
    viewModelScope.launch {
      _uiEventChannel.send(DrawerUiEvent.OnLinkItemClick(value = value))
    }
  }


  fun onEvent(event: DrawerEvent) = when (event) {
    is DrawerEvent.OnExpandItemClick -> state = state.copy(
      expandedIndex = if (state.expandedIndex != event.index) event.index else null
    )
    is DrawerEvent.OnLinkItemClick -> onLinkItemClick(event.value)
  }
}