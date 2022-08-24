package ru.autopulse05.android.shared.data.remote.dto

import kotlinx.serialization.Serializable
import ru.autopulse05.android.shared.domain.model.Image

@Serializable
data class ImageDto(
  val name: String,
  val order: Int
)

fun ImageDto.toImage() = Image(name = name)