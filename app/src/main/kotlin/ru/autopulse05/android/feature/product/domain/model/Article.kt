package ru.autopulse05.android.feature.product.domain.model

import ru.autopulse05.android.feature.product.data.remote.dto.ArticleDto


data class Article(
  val brand: String,
  val number: String
)

fun Article.toArticleDto() =
  ArticleDto(
    brand = brand,
    number = number
  )