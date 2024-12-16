package com.example.deezermusicapplication.presentation.components.content

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PressableContent(
    modifier: Modifier = Modifier,
    corners: Dp = 8.dp,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color(0xFFDBD3FF),
    containerColor: Color = Color.White,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    contentAlignment: Alignment? = null,
    content: @Composable () -> Unit
) {

    val isPressed by interactionSource.collectIsPressedAsState()
    val offset by animateDpAsState(
        targetValue = if ((isPressed) && enabled) borderWidth else borderWidth + (2.5).dp,
        label = "offset"
    )
    val distanceMultiplier by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.8f else 1f,
        label = "distance_multiplier"
    )

    Box(modifier = modifier
        .graphicsLayer {
            scaleX *= distanceMultiplier
            scaleY *= distanceMultiplier
        }
        .clip(RoundedCornerShape(corners))
        .border(
            width = borderWidth.minus(1.dp),
            color = borderColor,
            shape = RoundedCornerShape(corners)
        )
        .drawBehind {
            drawRoundRect(
                size = Size(width = size.width, height = size.height),
                color = borderColor,
                cornerRadius = CornerRadius(
                    corners.toPx(), corners.toPx()
                )
            )

            drawRoundRect(
                size = Size(
                    width = size.width - borderWidth.toPx() * 2f,
                    height = (size.height - (offset).toPx() * 2f)
                ),
                color = containerColor,
                topLeft = Offset(
                    x = (borderWidth).toPx(),
                    y = (borderWidth).toPx()
                ),
                cornerRadius = CornerRadius(
                    (corners).toPx(),
                    (corners + borderWidth * 5f).toPx() / 2f
                )
            )
        }
        .clickable(
            enabled = enabled,
            interactionSource = interactionSource,
            indication = null
        ) { onClick?.invoke() },
        contentAlignment = contentAlignment ?: Alignment.Center
    ) {
        content()
    }


}

@Preview
@Composable
fun PressableContentPrev() {
    PressableContent {
        Text(modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp), text = "HELLO")
    }
}