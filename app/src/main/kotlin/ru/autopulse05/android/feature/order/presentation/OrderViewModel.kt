package ru.autopulse05.android.feature.order.presentation

import android.app.Application
import android.content.Context
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
import ru.autopulse05.android.feature.order.domain.use_case.OrderObject
import ru.autopulse05.android.feature.order.domain.use_case.OrderUseCases
import ru.autopulse05.android.feature.order.presentation.util.OrderEvent
import ru.autopulse05.android.feature.order.presentation.util.OrderShipmentMode
import ru.autopulse05.android.feature.order.presentation.util.OrderState
import ru.autopulse05.android.feature.order.presentation.util.OrderUiEvent
import ru.autopulse05.android.feature.preferences.presentation.Preferences
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.feature.shipment.domain.use_case.ShipmentUseCases
import ru.autopulse05.android.feature.user.domain.repository.UserRepository
import ru.autopulse05.android.shared.domain.use_case.SharedUseCases
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
  application: Application,
  private val orderUseCases: OrderUseCases,
  private val cartUseCases: CartUseCases,
  private val userRepository: UserRepository,
  private val shipmentUseCases: ShipmentUseCases,
  private val sharedUseCases: SharedUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var orderJob: Job? = null
  private var addPayJob: Job? = null
  private var getShipmentMethodsJob: Job? = null
  private var getPaymentJob: Job? = null
  private var getUserJob: Job? = null
  private var getOfficesJob: Job? = null
  private val _uiEventChannel = Channel<OrderUiEvent>()


  var state by mutableStateOf(OrderState())
  val uiEvents = _uiEventChannel.receiveAsFlow()


  private fun onSubmit() {
   if(state.shipmentMode==OrderShipmentMode.Pickup) {
     orderJob = orderUseCases
       .order(
         login = preferencesState.login,
         passwordHash = preferencesState.passwordHash,
         paymentMethod = state.paymentMethod.value!!.id,
         shipmentMethod = if(state.shipmentMethod.value==null) null else  state.shipmentMethod.value!!.id,
         shipmentAddress = "0",
         shipmentOffice = if(state.shipmentMode==OrderShipmentMode.Pickup) state.office.value!!.id else null,
         shipmentDate = null,
         comment = state.comment.value,
         wholeOrderMode = state.wholeOrderMode,
         positionIds = state.positions.map { it.positionId }.toTypedArray()
       )
       .onEach { data ->
         when (data) {
           is Data.Error -> {
             Log.e(TAG, "Error during making order: ${data.message}")

             _uiEventChannel.send(OrderUiEvent.Toast(text = stringResource(R.string.error)))

             state = state.copy(isLoading = false)
           }
           is Data.Loading -> state = state.copy(isLoading = true)
           is Data.Success -> {
             cartUseCases.update(
               login = preferencesState.login,
               passwordHash = preferencesState.passwordHash
             )
             if(!state.paymentMethod.value!!.name.contentEquals("Наличные при получении")) addPay(data.value)
             else _uiEventChannel.send(OrderUiEvent.Success)
           }
         }
       }
       .launchIn(viewModelScope)
   } else {
     orderUseCases
       .address(
         login = preferencesState.login,
         passwordHash = preferencesState.passwordHash,
         address = state.shipmentAddress.value
       )
       .onEach {
         orderJob = orderUseCases
           .order(
             login = preferencesState.login,
             passwordHash = preferencesState.passwordHash,
             paymentMethod = state.paymentMethod.value!!.id,
             shipmentMethod = if(state.shipmentMethod.value==null) null else  state.shipmentMethod.value!!.id,
             shipmentAddress = if(state.shipmentMode==OrderShipmentMode.Pickup) "0" else it.id.toString(),
             shipmentOffice = if(state.shipmentMode==OrderShipmentMode.Pickup) state.office.value!!.id else null,
             shipmentDate = null,
             comment = state.comment.value,
             wholeOrderMode = state.wholeOrderMode,
             positionIds = state.positions.map { it.positionId }.toTypedArray()
           )
           .onEach { data ->
             when (data) {
               is Data.Error -> {
                 Log.e(TAG, "Error during making order: ${data.message}")

                 _uiEventChannel.send(OrderUiEvent.Toast(text = stringResource(R.string.error)))

                 state = state.copy(isLoading = false)
               }
               is Data.Loading -> state = state.copy(isLoading = true)
               is Data.Success -> {
                 cartUseCases.update(
                   login = preferencesState.login,
                   passwordHash = preferencesState.passwordHash
                 )
                  if(!state.paymentMethod.value!!.name.contentEquals("Наличные при получении")) addPay(data.value)
                 else _uiEventChannel.send(OrderUiEvent.Success)
               }
             }
           }
           .launchIn(viewModelScope)
       }
       .launchIn(viewModelScope)
   }
  }

  private fun addPay(value: OrderObject) {
    addPayJob?.cancel()

    addPayJob = orderUseCases.addPay(
      login = preferencesState.adminLogin,
      passwordHash = preferencesState.adminPasswordHash,
      id = state.user!!.id,
      date = value.date,
      sum = value.sum,
      comment = "Оплата заказа"
    ).onEach {
        data ->
      when (data) {
        is Data.Error -> {
          Log.e(TAG, "Error during making order: ${data.message}")

          _uiEventChannel.send(OrderUiEvent.Toast(text = stringResource(R.string.error)))

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


  private fun getShipmentMethods() {
    getShipmentMethodsJob?.cancel()

    getShipmentMethodsJob = shipmentUseCases
      .getShipmentMethods(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash
      )
      .onEach { data ->
        state = when (data) {
          is Data.Error -> {
            Log.e(TAG, "Error during getting shipment methods: ${data.message}")

            _uiEventChannel.send(OrderUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(shipmentMethod = state.shipmentMethod.copy(isShowing = false))
          }
          is Data.Loading -> state.copy(
            shipmentMethod = state.shipmentMethod.copy(isShowing = false)
          )
          is Data.Success -> state.copy(
            shipmentMethod = state.shipmentMethod.copy(
              isShowing = true,
              items = data.value,
              value = data.value.first()
            )
          )
        }
      }
      .launchIn(viewModelScope)
  }

  private fun getOffices() {
    getOfficesJob?.cancel()
    getOfficesJob = orderUseCases
      .offices(
        login = preferencesState.login,
        passwordHash = preferencesState.passwordHash
      )
      .onEach { data ->
        state = when (data) {
          is Data.Error -> {
            Log.e(TAG, "Error during getting shipment methods: ${data.message}")

            _uiEventChannel.send(OrderUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(shipmentMethod = state.shipmentMethod.copy(isShowing = false))
          }
          is Data.Loading -> state.copy(
            office = state.office.copy(isShowing = false)
          )
          is Data.Success -> state.copy(
            office = state.office.copy(
              items = data.value,
              value = data.value.first(),
              isShowing = true
            )
          )
        }
      }
      .launchIn(viewModelScope)
  }



  private fun getPayment() {
    getPaymentJob?.cancel()
    getPaymentJob = orderUseCases
      .payment(
        login = preferencesState.login,
        password = preferencesState.passwordHash
      )
      .onEach { data ->
        state = when(data) {
          is Data.Success -> state.copy(
            paymentMethod = state.paymentMethod.copy(
              items = data.value,
              value = data.value.first()
            )
          )
          else -> state.copy()
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onShipmentModeChange(value: OrderShipmentMode) {
    when (value) {
      OrderShipmentMode.Delivery -> if (state.shipmentMethod.items.isEmpty()) getShipmentMethods()
      OrderShipmentMode.Pickup -> getShipmentMethodsJob?.cancel()
    }
    state = state.copy(shipmentMode = value)
  }

  private fun getUser() {
    userRepository
      .get()
      .onEach { entity -> state = state.copy(user = entity) }
      .launchIn(viewModelScope)
  }

  override fun onPreferencesChange(preferences: Preferences) {
    super.onPreferencesChange(preferences)

    if (preferences.isLoggedIn) {
      getUser()
    }
  }

  init {
    getPreferences()
    getOffices()
    getPayment()
    getUser()
  }


  fun onEvent(event: OrderEvent): Unit = when (event) {
    is OrderEvent.InitialValuesChange -> state = state.copy(
      positions = event.positions,
      isLoading = false
    )
    is OrderEvent.Submit -> onSubmit()
    is OrderEvent.TermsAgreementChange -> state = state.copy(
      termsAgreement = event.value
    )
    is OrderEvent.WholeOrderModeChange -> state = state.copy(
      wholeOrderMode = event.value
    )
    is OrderEvent.CommentChange -> state = state.copy(
      comment = state.comment.copy(
        value = event.value,
        error = null
      )
    )
    is OrderEvent.ShipmentModeChange -> onShipmentModeChange(event.value)
    is OrderEvent.ShipmentAddressChange -> state = state.copy(
      shipmentAddress = state.shipmentAddress.copy(
        value = event.value,
        error = null
      )
    )
    is OrderEvent.ShipmentMethodChange -> state = state.copy(
      shipmentMethod = state.shipmentMethod.copy(
        value = event.value
      )
    )
    is OrderEvent.PositionsVisibilityChange -> state = state.copy(
      isPositionsVisible = event.value
    )
    is OrderEvent.OfficeChange -> state = state.copy(
      office = state.office.copy(value = event.value)
    )
    is OrderEvent.PaymentChange -> state = state.copy(
      paymentMethod = state.paymentMethod.copy(value = event.value)
    )
  }
}