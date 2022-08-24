package ru.autopulse05.android.feature.vin.domain.use_case

import ru.autopulse05.android.shared.domain.util.Data

class VinValidatePartsUseCase {
  operator fun invoke(value: List<String>): Data<Unit> = when {
    value.isEmpty() -> Data.Error(
      message = "Список должен содержать хотя-бы одну деталь"
    )
    else -> Data.Success(value = Unit)
  }
}