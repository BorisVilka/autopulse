package ru.autopulse05.android.feature.vin.presentation.list.util

import ru.autopulse05.android.feature.user.domain.model.User
import ru.autopulse05.android.feature.vin.data.remote.dto.VinDto

data class VinListState(
    var list: List<VinDto>? = null,
    var user: User? = null
)