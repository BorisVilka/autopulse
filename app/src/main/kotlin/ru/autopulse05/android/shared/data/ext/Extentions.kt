package ru.autopulse05.android.shared.data.ext

import com.google.gson.Gson
import java.lang.reflect.Type
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

fun String.toUTF8(): String {
  return URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
}

fun String.toMD5(): String {
  val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
  return bytes.toHex()
}

fun ByteArray.toHex(): String {
  return joinToString("") { "%02x".format(it) }
}

fun <A> String.fromJson(type: Type): A {
  return Gson().fromJson(this, type)
}

fun <A> A.toJson(): String? {
  return Gson().toJson(this)
}

