package ru.autopulse05.android.feature.product.presentation.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.BrandWhite
import ru.autopulse05.android.shared.presentation.util.PresentationText

@Composable
fun ProductImage(
  url: String
) {
  Box(
    modifier = Modifier.background(color = Color.BrandWhite)
  ) {
    GlideImage(
      imageModel = url,
      contentDescription = PresentationText.Resource(R.string.product_photo).asString(),
      modifier = Modifier.height(200.dp)
    )
  }
}

@Preview(
  name = "Product Image",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProductImagePreview() {
  ProductImage(
    url = "https://zoolog.guru/wp-content/uploads/2019/03/post_5c9326020da78.jpeg"
  )
}