package ru.autopulse05.android.feature.basket.presentation.util

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import ru.autopulse05.android.feature.basket.domain.model.BasketItem

data class BasketState(
  val isLoading: Boolean = true,
  val selected: PersistentList<BasketItem> = persistentListOf(),
  val items: List<BasketItem> = listOf()
)
