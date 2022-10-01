package ru.autopulse05.android.feature.order.domain.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.order.domain.model.Order
import ru.autopulse05.android.feature.order.domain.model.WholeOrderMode
import ru.autopulse05.android.shared.data.remote.ResponseStatus
import ru.autopulse05.android.shared.domain.util.Data

class OrderUseCase(
  private val remoteService: OrderRemoteService
) {

  operator fun invoke(
    login: String,
    passwordHash: String,
    paymentMethod: String?,
    shipmentMethod: String?,
    shipmentAddress: String?,
    shipmentOffice: String?,
    shipmentDate: String?,
    comment: String?,
    wholeOrderMode: WholeOrderMode,
    positionIds: Array<Int>
  ): Flow<Data<OrderObject>> = flow {
    try {
      emit(Data.Loading())

      val createOrdersDto = remoteService
        .order(
          login = login,
          passwordHash = passwordHash,
          paymentMethod = paymentMethod,
          shipmentMethod = shipmentMethod,
          shipmentAddress = shipmentAddress,
          shipmentOffice = shipmentOffice,
          shipmentDate = shipmentDate,
          comment = comment,
          wholeOrderMode = wholeOrderMode.value,
          positionIds = positionIds
        )
      var obj = OrderObject("",0)
     for(i in createOrdersDto.orders.values) {
       Log.d("TAG", i.date + " " + i.sum);
       obj.date = i.date
       obj.sum+=i.sum.toInt()
     }
     emit(Data.Success(value = obj))
    } catch (e: Exception) {
      e.printStackTrace()
      emit(Data.Error(message = e.message))
    }
  }
}

data class OrderObject(
  var date: String,
  var sum: Int
)