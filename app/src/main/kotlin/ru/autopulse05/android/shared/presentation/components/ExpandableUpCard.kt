package ru.autopulse05.android.shared.presentation.components

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ru.autopulse05.android.R
import ru.autopulse05.android.core.presentation.ui.theme.ANIMATION_DURATION_MS
import ru.autopulse05.android.core.presentation.ui.theme.SpaceSmall
import ru.autopulse05.android.shared.presentation.util.PresentationText

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableUpCard(
  modifier: Modifier = Modifier,
  title: PresentationText,
  onClick: (Boolean) -> Unit,
  expanded: Boolean,
  cardExpandedBackgroundColor: Color = MaterialTheme.colors.secondary,
  cardCollapsedBackgroundColor: Color = MaterialTheme.colors.secondary,
  shape: Shape = RoundedCornerShape(16.dp),
  textStyle: TextStyle = MaterialTheme.typography.body1,
  textColor: Color = MaterialTheme.colors.onSecondary,
  content: @Composable () -> Unit,
  visibleContent: @Composable () -> Unit = {}
) = ExpandableUpCard(
  modifier = modifier,
  expanded = expanded,
  cardExpandedBackgroundColor = cardExpandedBackgroundColor,
  cardCollapsedBackgroundColor = cardCollapsedBackgroundColor,
  shape = shape,
  selector = { transition ->
    val arrowRotationDegree by transition.animateFloat({
      tween(durationMillis = ANIMATION_DURATION_MS)
    }, label = "") {
      if (expanded) 0f else 180f
    }

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(SpaceSmall)
        .clickable { onClick(!expanded) },
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        text = title.asString(),
        style = textStyle,
        color = textColor,
        modifier = Modifier.weight(1f)
      )
      Icon(
        modifier = Modifier.rotate(arrowRotationDegree),
        painter = painterResource(id = R.drawable.ic_expand_less_24),
        contentDescription = "Expandable Arrow",
        tint = textColor,
      )
    }
  },
  innerContent = content,
  visibleContent = visibleContent
)


@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableUpCard(
  modifier: Modifier = Modifier,
  expanded: Boolean,
  cardExpandedBackgroundColor: Color,
  cardCollapsedBackgroundColor: Color,
  shape: Shape = RoundedCornerShape(16.dp),
  visibleContent: @Composable () -> Unit = {},
  innerContent: @Composable () -> Unit,
  selector: @Composable (Transition<Boolean>) -> Unit,
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

  val cardBgColor by transition.animateColor({
    tween(durationMillis = ANIMATION_DURATION_MS)
  }, label = "") { if (expanded) cardExpandedBackgroundColor else cardCollapsedBackgroundColor }

  val cardElevation by transition.animateDp({
    tween(durationMillis = ANIMATION_DURATION_MS)
  }, label = "") { if (expanded) 24.dp else 4.dp }

  Card(
    backgroundColor = cardBgColor,
    elevation = cardElevation,
    shape = shape,
    modifier = modifier.fillMaxWidth()
  ) {
    Column {
      Box {
        visibleContent()
      }
      AnimatedVisibility(
        visible = expanded,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
      ) {
        innerContent()
      }
      Box {
        selector(transition)
      }
    }
  }
}