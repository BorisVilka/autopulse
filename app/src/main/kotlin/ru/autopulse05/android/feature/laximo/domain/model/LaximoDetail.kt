package ru.autopulse05.android.feature.laximo.domain.model

data class LaximoDetail(
  val codeOnImage: String,
  val name: String,
  val oem: String? = null,
  val ssd: String? = null,
  val note: String? = null,
  val filter: String? = null,
  val flag: String? = null,
  val match: String? = null,
  val designation: String? = null,
  val applicableModels: String? = null,
  val partSpec: String? = null,
  val color: String? = null,
  val shape: String? = null,
  val standard: String? = null,
  val material: String? = null,
  val size: String? = null,
  val featureDescription: String? = null,
  val prodStart: String? = null,
  val prodEnd: String? = null
)