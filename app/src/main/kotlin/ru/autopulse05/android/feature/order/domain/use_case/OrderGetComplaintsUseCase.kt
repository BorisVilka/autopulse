package ru.autopulse05.android.feature.order.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.order.data.remote.OrderRemoteService
import ru.autopulse05.android.feature.order.data.remote.dto.toComplaint
import ru.autopulse05.android.feature.order.domain.model.Complaint
import ru.autopulse05.android.shared.domain.util.Data

class OrderGetComplaintsUseCase(
  private val remoteService: OrderRemoteService
) {


}