package ru.autopulse05.android.feature.user.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.user.data.remote.UserRemoteService
import ru.autopulse05.android.feature.user.data.remote.dto.PaymentDto
import ru.autopulse05.android.shared.domain.util.Data
import kotlin.math.log

class UserGetPaymentsUseCase(
    private val remoteService: UserRemoteService
) {

    operator fun invoke(
       login: String,
       password: String,
       id: String,
       start: String,
       end: String
    ): Flow<Data<List<PaymentDto>>> = flow {
        try {
            emit(Data.Loading())
            val ans = remoteService.getPayments(
                login = login,
                passwordHash = password,
                id = id,
                start = start,
                end = end
            )
            emit(Data.Success(value = ans))

        } catch (e: Exception) {
            emit(Data.Error(message = e.message.toString()))
        }
    }
}