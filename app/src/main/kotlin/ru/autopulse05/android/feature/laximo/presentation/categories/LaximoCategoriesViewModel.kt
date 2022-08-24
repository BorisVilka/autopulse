package ru.autopulse05.android.feature.laximo.presentation.categories

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.laximo.domain.model.LaximoCategory
import ru.autopulse05.android.feature.laximo.domain.use_case.LaximoUseCases
import ru.autopulse05.android.feature.laximo.presentation.categories.util.LaximoCategoriesEvent
import ru.autopulse05.android.feature.laximo.presentation.categories.util.LaximoCategoriesState
import ru.autopulse05.android.feature.laximo.presentation.categories.util.LaximoCategoriesUiEvent
import ru.autopulse05.android.feature.laximo.presentation.categories.util.LaximoCategoryData
import ru.autopulse05.android.feature.preferences.presentation.PreferencesViewModel
import ru.autopulse05.android.shared.domain.util.Data
import javax.inject.Inject

@HiltViewModel
class LaximoCategoriesViewModel @Inject constructor(
  application: Application,
  private val laximoUseCases: LaximoUseCases
) : PreferencesViewModel(application = application) {
  companion object {
    private val TAG = this::class.java.name
  }

  private var getCategoriesJob: Job? = null
  private val _uiEventChannel = Channel<LaximoCategoriesUiEvent>()

  var state by mutableStateOf(LaximoCategoriesState())
  val uiEvents = _uiEventChannel.receiveAsFlow()

  private fun getCategories() {
    getCategoriesJob?.cancel()

    getCategoriesJob = laximoUseCases
      .getCategories(
        login = preferencesState.laximoLogin,
        password = preferencesState.laximoPassword,
        locale = preferencesState.locale,
        catalog = state.catalog,
        vehicleId = state.vehicleId,
        ssd = state.ssd
      )
      .onEach { data ->
        state = when (data) {
          is Data.Success -> {
            val tmpMap = mutableMapOf<Int, MutableList<LaximoCategory>>()

            for (category in data.value) tmpMap[category.id] = mutableListOf()

            for (category in data.value) if (category.parentId != null) {
              tmpMap[category.parentId]?.add(category)
            }

            fun convertCategory(category: LaximoCategory): LaximoCategoryData {
              val childrens = tmpMap[category.id]!!
              val name = category.name.substringAfter(". ")

              return if (childrens.isEmpty()) LaximoCategoryData.Basic(
                id = category.id,
                name = name,
                code = category.code,
                ssd = category.ssd
              ) else LaximoCategoryData.Expandable(
                id = category.id,
                name = name,
                code = category.code,
                expanded = false,
                childrens = childrens.map { convertCategory(it) },
                ssd = category.ssd
              )
            }

            state.copy(
              categories = data.value
                .filter { it.parentId == null }
                .map { convertCategory(it) }
                .toPersistentList(),
              isLoading = false
            )
          }
          is Data.Error -> {
            Log.e(TAG, "Error during getting categories: ${data.message}")

            _uiEventChannel.send(LaximoCategoriesUiEvent.Toast(text = stringResource(R.string.error)))

            state.copy(isLoading = false)
          }
          is Data.Loading -> state.copy(isLoading = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun onInitialValuesChange(
    catalog: String,
    vehicleId: String,
    ssd: String
  ) {
    state = state.copy(
      catalog = catalog,
      vehicleId = vehicleId,
      ssd = ssd
    )

    getCategories()
  }

  private fun onExpandableCategoryClick(value: LaximoCategoryData.Expandable) {
    state = state.copy(
      categories = state.categories.mutate { categories ->
        val index = categories.indexOf(value)

        categories[index] = LaximoCategoryData.Expandable(
          id = value.id,
          name = value.name,
          code = value.code,
          expanded = !value.expanded,
          childrens = value.childrens,
          ssd = value.ssd
        )
      }
    )
  }

  private fun onBasicCategoryClick(value: LaximoCategoryData.Basic) {
    viewModelScope.launch {
      _uiEventChannel.send(
        LaximoCategoriesUiEvent.BasicCategoryClick(
          value = value,
          catalog = state.catalog,
          vehicleId = state.vehicleId,
          ssd = state.ssd,
        )
      )
    }
  }

  init {
    getPreferences()
  }

  fun onEvent(event: LaximoCategoriesEvent) = when (event) {
    is LaximoCategoriesEvent.InitialValuesChange -> onInitialValuesChange(
      catalog = event.catalog,
      vehicleId = event.vehicleId,
      ssd = event.ssd
    )
    is LaximoCategoriesEvent.ExpandableCategoryClick -> onExpandableCategoryClick(event.value)
    is LaximoCategoriesEvent.BasicCategoryClick -> onBasicCategoryClick(event.value)
  }
}