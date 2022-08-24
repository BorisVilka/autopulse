package ru.autopulse05.android.feature.laximo.presentation.unit.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.autopulse05.android.core.presentation.ui.theme.SpaceNormal
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.feature.laximo.domain.model.LaximoDetail

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailItem(
  detail: LaximoDetail,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  subject: PublishSubject<String>,
  scope: CoroutineScope
) {
  val requester = remember {
    BringIntoViewRequester()
  }
  val tmp = MaterialTheme.colors.surface
  val clr = remember {
    Animatable(tmp)
  }
  var disposable = subject.subscribe {
    scope.launch {
      if(it==detail.codeOnImage) {
        Log.d("TAG","${it} ${detail.codeOnImage}")
        requester.bringIntoView()
        clr.animateTo(Color.Cyan, animationSpec = tween(1000))
        clr.animateTo(tmp, animationSpec = tween(1000))
      }
    }
  }
  Column(
    modifier = modifier
      .clickable { onClick() }
      .shadow(elevation = SpaceSmall)
      .background(color = clr.value, shape = MaterialTheme.shapes.small)
      .padding(SpaceNormal)
      .fillMaxWidth()
      .bringIntoViewRequester(requester)
  ) {
    Text(
      text = "${detail.codeOnImage}. ${detail.name}",
      style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(SpaceNormal))

    if (detail.oem != null) {
      Text(text = detail.oem)
    }
  }
}

