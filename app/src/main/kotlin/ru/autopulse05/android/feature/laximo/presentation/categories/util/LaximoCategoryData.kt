package ru.autopulse05.android.feature.laximo.presentation.categories.util

sealed class LaximoCategoryData(
  open val id: Int,
  open val name: String,
  open val code: String?,
  open val ssd: String,
) {

  data class Expandable(
    override val id: Int,
    override val name: String,
    override val ssd: String,
    override val code: String?,
    val expanded: Boolean,
    val childrens: List<LaximoCategoryData>
  ) : LaximoCategoryData(
    id = id,
    name = name,
    code = code,
    ssd = ssd
  )

  data class Basic(
    override val id: Int,
    override val name: String,
    override val ssd: String,
    override val code: String?,
  ) : LaximoCategoryData(
    id = id,
    name = name,
    code = code,
    ssd = ssd
  )
}