package ru.autopulse05.android.feature.product.data.remote.dto

import ru.autopulse05.android.feature.product.domain.model.Article

data class ArticleDto(
  val brand: String,
  val number: String
)

fun ArticleDto.toArticle() =
  Article(
    brand = brand,
    number = number
  )