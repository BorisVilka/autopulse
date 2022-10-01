package ru.autopulse05.android.feature.store.presentation.util

import androidx.annotation.DrawableRes
import ru.autopulse05.android.R
import ru.autopulse05.android.feature.laximo.presentation.util.LaximoScreens
import ru.autopulse05.android.feature.vin.presentation.util.VinScreens
import ru.autopulse05.android.shared.presentation.util.PresentationText

sealed class MenuItems(
  val title: PresentationText,
  @DrawableRes val icon: Int,
  val description: PresentationText,
  val route: String
) {

  object OriginalCatalogue : MenuItems(
    icon = R.mipmap.auto_catalog_foreground,
    title = PresentationText.Resource(R.string.original_catalogue),
    description = PresentationText.Resource(R.string.search_by_vin),
    route = LaximoScreens.Catalogs.route
  )

  object Catalogue : MenuItems(
    icon = R.mipmap.catalogs_foreground,
    title = PresentationText.Resource(R.string.auto_catalogue),
    description = PresentationText.Resource(R.string.selection_of_spare_parts),
    route = LaximoScreens.Auto.route
  )

  object RequestByVin : MenuItems(
    icon = R.mipmap.vin_request1_foreground,
    title = PresentationText.Resource(R.string.request_by_vin),
    description = PresentationText.Resource(R.string.manager_select_parts),
    route = VinScreens.List.route
  )

}
