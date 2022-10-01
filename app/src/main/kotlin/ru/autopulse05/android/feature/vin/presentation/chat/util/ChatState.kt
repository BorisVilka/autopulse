package ru.autopulse05.android.feature.vin.presentation.chat.util

import androidx.compose.runtime.mutableStateListOf
import ru.autopulse05.android.feature.user.domain.model.User
import ru.autopulse05.android.feature.vin.data.remote.dto.ChatDto

data class ChatState (
    var user: User? = null,
    var id: String = "",
    var message: String = "",
    var chat: MutableList<ChatDto> = mutableStateListOf()
)