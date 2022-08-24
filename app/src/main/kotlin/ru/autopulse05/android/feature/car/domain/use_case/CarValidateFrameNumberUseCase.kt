package ru.autopulse05.android.feature.car.domain.use_case

import ru.autopulse05.android.shared.domain.util.Data

class CarValidateFrameNumberUseCase {
  operator fun invoke(value: String): Data<Unit> = when {
    value.isBlank() -> Data.Error(
      message = "Номер кузова не может быть пустым"
    )
    else -> Data.Success(value = Unit)
  }
}