package ru.autopulse05.android.feature.user.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "users")
@Serializable
data class User(
  @PrimaryKey val id: String,
  val code: String,
  val email: String,
  val name: String,
  val mobile: String,
  val organization: String
)