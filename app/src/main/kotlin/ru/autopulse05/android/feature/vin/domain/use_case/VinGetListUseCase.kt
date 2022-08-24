package ru.autopulse05.android.feature.vin.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.vin.data.remote.VinRemoteService
import ru.autopulse05.android.feature.vin.data.remote.dto.toVinInfo
import ru.autopulse05.android.feature.vin.domain.model.VinInfo
import ru.autopulse05.android.shared.domain.util.Data

class VinGetListUseCase(
  private val remoteService: VinRemoteService
) {


}