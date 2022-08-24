package ru.autopulse05.android.feature.search.data.remote

object OnlineStocksMode {
  /*
  * Parts from Online warehouses will be included into the response.
  */
  const val ON = 1

  /*
  * Parts from Online warehouses will not be included into the response.
  * Will increase the speed of response.
  */
  const val OFF = 0
}

object AnalogsMode {
  /*
  * Part analogs will be included into the response.
  */
  const val ON = 0

  /*
  * Part analogs will not be included into the response.
  */
  const val OFF = 1
}

object OnlineFilteringMode {
  /*
  * Include filters:
  *   - “The minimum probability of delivery”;
  *   - “minimum availability”;
  *   - “show only the exact availability”;
  *   - “limit the delivery time, in watches”.
  */
  const val ON = 0

  /*
  * Exclude filters:
  *   - “The minimum probability of delivery”;
  *   - “minimum availability”;
  *   - “show only the exact availability”;
  *   - “limit the delivery time, in watches”.
  *
  * "Trust the crosses" turns on.
  */
  const val OFF = 1
}