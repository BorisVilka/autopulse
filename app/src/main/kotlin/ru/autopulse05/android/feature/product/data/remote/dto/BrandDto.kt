package ru.autopulse05.android.feature.product.data.remote.dto

import ru.autopulse05.android.feature.product.domain.model.Brand

data class BrandDto(
  val name: String,
  val aliases: List<String>
)

fun BrandDto.toBrand() =
  Brand(
    name = name,
    aliases = aliases
  )