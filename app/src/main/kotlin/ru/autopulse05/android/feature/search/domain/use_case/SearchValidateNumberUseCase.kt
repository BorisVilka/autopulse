package ru.autopulse05.android.feature.search.domain.use_case

import ru.autopulse05.android.shared.domain.util.Data

class SearchValidateNumberUseCase {
  operator fun invoke(value: String): Data<Unit> = when {
    value.length < 2 -> Data.Error()
    else -> Data.Success(value = Unit)
  }
}