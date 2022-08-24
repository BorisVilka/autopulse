package ru.autopulse05.android.feature.search.domain.use_case

data class SearchUseCases(
  val getBrands: SearchGetBrandsUseCase,
  val getArticle: SearchGetArticlesUseCase,
  val batch: SearchBatchUseCase,
  val getHistory: SearchGetHistoryUseCase,
  val getTips: SearchGetTipsUseCase,

  // Validation
  val validateNumber: SearchValidateNumberUseCase
)