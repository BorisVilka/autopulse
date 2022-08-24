package ru.autopulse05.android.feature.laximo.data.remote.dto

import ru.autopulse05.android.feature.laximo.domain.model.LaximoCategory


data class LaximoCategoryDto(
  val categoryId: Int,
  val name: String,
  val code: String?,
  val ssd: String,
  val childrens: Boolean?,
  val parentCategoryId: Int?
)

fun LaximoCategoryDto.toLaximoCategory() = LaximoCategory(
  id = categoryId,
  name = name,
  code = code,
  ssd = ssd,
  childrens = childrens,
  parentId = parentCategoryId
)
