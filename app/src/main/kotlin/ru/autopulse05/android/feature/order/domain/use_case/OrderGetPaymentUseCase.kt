package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.payment.domain.model.PaymentMethod
import ru.autopulse05.android.shared.domain.util.Data

class OrderGetPaymentUseCase(
    private val remoteService: OrderRemoteService
) {

    operator fun invoke(
        login: String,
        password: String
    ):
            Flow<Data<List<PaymentMethod>>> = flow {
        try {
           emit(Data.Loading())
            val ans = remoteService
                .getPayment(
                    login = login,
                    passwordHash = password
                )
                .map { PaymentMethod(id = it.id.toString(), name = it.name) }
                .toList()
            emit(Data.Success(value = ans))
        } catch (e: Exception) {
            emit(Data.Error(message = e.message))
        }
    }
}