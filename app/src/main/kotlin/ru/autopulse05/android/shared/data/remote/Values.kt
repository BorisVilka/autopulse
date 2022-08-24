package ru.autopulse05.android.shared.data.remote

object ResponseStatus {
  const val SUCCESS = 1
  const val ERROR = 0
}

object WholeOrderMode {

  /*
  * Place whole order at one time
  */
  const val ON = 1

  /*
  * Place an order in parts or not
  */
  const val OFF = 0
}