package ru.autopulse05.android.feature.product.domain.use_case

data class ProductUseCases(
  // Advices
  val getAdvices: ProductGetAdvicesUseCase,
  val advicesBatch: ProductAdvicesBatchUseCase,

  // Articles
  val getArticlesBrands: ProductGetBrandsUseCase,
  val getArticlesInfo: ProductGetInfoUseCase
)