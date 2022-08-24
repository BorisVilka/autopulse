package ru.autopulse05.android.feature.laximo.data.remote.dto

import ru.autopulse05.android.feature.laximo.domain.model.LaximoDetail


data class LaximoDetailDto(
  val codeOnImage: String,
  val name: String,
  val oem: String?,
  val ssd: String?,
  val note: String?,
  val filter: String?,
  val flag: String?,
  val match: String?,
  val designation: String?,
  val applicableModels: String?,
  val partSpec: String?,
  val color: String?,
  val shape: String?,
  val standard: String?,
  val material: String?,
  val size: String?,
  val featureDescription: String?,
  val prodStart: String?,
  val prodEnd: String?
)

fun LaximoDetailDto.toLaximoDetail() = LaximoDetail(
  codeOnImage = codeOnImage,
  name = name,
  oem = oem,
  ssd = ssd,
  note = note,
  filter = filter,
  flag = flag,
  match = match,
  designation = designation,
  applicableModels = applicableModels,
  partSpec = partSpec,
  color = color,
  shape = shape,
  standard = standard,
  material = material,
  size = size,
  featureDescription = featureDescription,
  prodStart = prodStart,
  prodEnd = prodEnd
)
