package ru.autopulse05.android.shared.presentation.util

abstract class Screens(
  prefix: String? = null,
  name: String
) {
  val route = "${if (prefix == null) "" else prefix + "_"}${name}_screen"

  fun <T> withArgs(vararg args: T) =
    buildString {
      append(route)
      args.forEach { append(it) }
    }
}