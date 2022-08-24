package ru.autopulse05.android.feature.order.data.remote


object ComplaintStatus {

  const val NEW = 1

  const val IN_WORK = 2

  const val REFUSAL = 3

  const val CONFIRMED = 4

  const val ISSUED = 5

  const val CANCELED = 6
}

object CreateComplaintPositionStatus {
  const val NEW = 1

  const val IN_WORK = 2

  const val REFUSAL = 3

  const val COMPLETED = 4

  const val ISSUED = 5

  const val CANCELED = 6

  const val REJECTED = 7

  const val CONFIRMED = 8
}


object ComplaintType {

  const val RETURN = 1

  const val REFUSAL = 2

  const val MARRIAGE = 3
}