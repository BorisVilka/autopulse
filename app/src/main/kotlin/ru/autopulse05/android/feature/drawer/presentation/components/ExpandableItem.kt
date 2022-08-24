package ru.autopulse05.android.feature.drawer.presentation.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.ANIMATION_DURATION_MS
import ru.autopulse05.android.core.presentation.ui.theme.BrandYellow
import ru.autopulse05.android.feature.drawer.presentation.util.DrawerItems

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableItem(
  item: DrawerItems.ExpandableItem,
  onClick: (DrawerItems.ExpandableItem) -> Unit,
  onLinkClick: (DrawerItems.LinkItem) -> Unit,
  expanded: Boolean,
) {
  val transitionState = remember {
    MutableTransitionState(expanded).apply {
      targetState = !expanded
    }
  }

  val transition = updateTransition(transitionState, label = "")

  val enterFadeIn = remember {
    fadeIn(
      animationSpec = TweenSpec(
        durationMillis = ANIMATION_DURATION_MS,
        easing = FastOutLinearInEasing
      )
    )
  }

  val enterExpand = remember {
    expandVertically(animationSpec = tween(ANIMATION_DURATION_MS))
  }

  val exitFadeOut = remember {
    fadeOut(
      animationSpec = TweenSpec(
        durationMillis = ANIMATION_DURATION_MS,
        easing = LinearOutSlowInEasing
      )
    )
  }

  val exitCollapse = remember {
    shrinkVertically(animationSpec = tween(ANIMATION_DURATION_MS))
  }

  val arrowRotationDegree by transition.animateFloat({
    tween(durationMillis = ANIMATION_DURATION_MS)
  }, label = "") {
    if (expanded) 0f else 180f
  }

  Column(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          color = if (expanded) Color.BrandYellow else Color.Transparent
        )
        .padding(
          vertical = 8.dp,
          horizontal = 12.dp
        )
        .clickable {
          onClick(item)
        },
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        text = item.header.asString(),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onSecondary
      )
      Icon(
        painter = painterResource(id = R.drawable.ic_expand_less_24),
        contentDescription = "Expandable Arrow",
        tint = MaterialTheme.colors.onSecondary,
        modifier = Modifier.rotate(arrowRotationDegree),
      )

    }
    AnimatedVisibility(
      visible = expanded,
      enter = enterExpand + enterFadeIn,
      exit = exitCollapse + exitFadeOut
    ) {
      LazyColumn(modifier = Modifier.background(MaterialTheme.colors.background)) {
        items(item.children.size) { index ->
          LinkItem(
            item = item.children[index],
            onClick = onLinkClick
          )
        }
      }
    }
  }

}

@Preview(
  name = "Drawer Expandable Item",
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DrawerExpandableItemPreview() {
  ExpandableItem(
    item = DrawerItems.Profile,
    expanded = false,
    onClick = {},
    onLinkClick = {}
  )
}

@Preview(
  name = "Drawer Expandable Item Expanded",
  showBackground = true
)
@Composable
fun DrawerExpandableItemExpandedPreview() {
  ExpandableItem(
    item = DrawerItems.Profile,
    expanded = true,
    onClick = { },
    onLinkClick = {}
  )
}