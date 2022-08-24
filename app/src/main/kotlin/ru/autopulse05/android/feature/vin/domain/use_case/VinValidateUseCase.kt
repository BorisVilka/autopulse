package ru.autopulse05.android.feature.vin.domain.use_case

import ru.autopulse05.android.shared.domain.util.Data
import java.util.*

class VinValidateUseCase {
  operator fun invoke(vin: String): Data<Unit> {
    val values = intArrayOf(
      1, 2, 3, 4, 5, 6, 7, 8, 0, 1, 2, 3, 4, 5, 0, 7, 0, 9,
      2, 3, 4, 5, 6, 7, 8, 9
    )
    val weights = intArrayOf(8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2)
    var s = vin
    s = s.replace("-".toRegex(), "")
    s = s.replace(" ".toRegex(), "")
    s = s.uppercase(Locale.getDefault())

    if (s.length != 17) return Data.Error("VIN number must be 17 characters")

    var sum = 0
    for (i in 0..16) {
      val c = s[i]
      var value: Int
      val weight = weights[i]

      // letter
      if (c in 'A'..'Z') {
        value = values[c - 'A']
        if (value == 0) return Data.Error("Illegal character: $c")
      } else if (c in '0'..'9') value = c - '0'
      else return Data.Error("Illegal character: $c")
      sum += weight * value
    }

    // check digit
    sum %= 11
    val check = s[8]
    return when {
      sum == 10 && check == 'X' || sum == transliterate(check) -> Data.Success(value = Unit)
      else -> Data.Error("Invalid VIN")
    }
  }

  private fun transliterate(check: Char): Int {
    return when (check) {
      'A', 'J' -> return 1
      'B', 'K', 'S' -> return 2
      'C', 'L', 'T' -> return 3
      'D', 'M', 'U' -> return 4
      'E', 'N', 'V' -> return 5
      'F', 'W' -> return 6
      'G', 'P', 'X' -> return 7
      'H', 'Y' -> return 8
      'R', 'Z' -> return 9
      else -> Character.getNumericValue(check)
    }
  }
}