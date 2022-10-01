package ru.autopulse05.android.feature.vin.presentation.detail.util

import ru.autopulse05.android.feature.user.domain.model.User

data class VinDetailState(
    val partsVisible: Boolean = false,
    val offersVisible: Boolean = false,
    var user: User? = null,
    var id: String? = null
)