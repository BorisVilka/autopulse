package ru.autopulse05.android.feature.order.domain.use_case

import ru.autopulse05.android.shared.domain.util.Data

class OrderValidateCommentUseCase {
  operator fun invoke(value: String): Data<Unit> = when {
    value.length > 100 -> Data.Error(
      message = "Максимальная длина комментария - 100 символов"
    )
    else -> Data.Success(value = Unit)
  }
}