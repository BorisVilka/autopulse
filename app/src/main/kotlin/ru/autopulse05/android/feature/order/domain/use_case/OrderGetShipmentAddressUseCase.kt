package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.order.data.remote.dto.ShipmentAddressDto

class OrderGetShipmentAddressUseCase(
    private val remoteService: OrderRemoteService
) {

    operator fun invoke(
        login: String,
        passwordHash: String,
        address: String
    ): Flow<ShipmentAddressDto> = flow {
        val ans  = remoteService
            .getAddress(
                login = login,
                passwordHash = passwordHash,
                address = address
            )
        emit(ans)
    }
}