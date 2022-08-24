package ru.autopulse05.android.feature.product.data.remote


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

object CrossImageMode {

  /*
  * Images of crosses will be included in response
  */
  const val ON = 1

  /*
  * Images of crosses will not be included in response
  */
  const val OFF = 1
}

object CrossSourceMode {
  /*
  * Crosses with a reliability of 99.9%.
  */
  const val STANDARD = "standard"

  /*
  * Crosses with a reliability of 95%.
  */
  const val COMMON = "common"

  /*
  * Crosses of several non-original catalogs.
  */
  const val COMMON_CAT = "common_cat"
}