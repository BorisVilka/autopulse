package ru.autopulse05.android.feature.laximo.domain.model

data class LaximoCategory(
  val id: Int,
  val name: String,
  val code: String?,
  val ssd: String,
  val childrens: Boolean?,
  val parentId: Int?
)