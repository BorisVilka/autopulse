package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.shared.domain.util.Data

class OrderAddPayUseCase (
    private val remoteService: OrderRemoteService
) {

    operator fun invoke(
        login: String,
        passwordHash: String,
        id: String,
        date: String,
        sum:Int,
        comment: String
    ): Flow<Data<Unit>> = flow {
        try {
            emit(Data.Loading())

            remoteService.addPay(
                login = login,
                passwordHash = passwordHash,
                id = id,
                date = date,
                sum = sum,
                comment = comment
            )
            emit(Data.Success(value = Unit))
        } catch (e: Exception) {
            emit(Data.Error(message = e.message))
        }
    }
}