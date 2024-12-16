package com.example.deezermusicapplication.presentation.components.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deezermusicapplication.presentation.components.content.PressableContent

@Composable
fun QuizOptionGameButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isCorrectAnswer: Boolean = false,
    optionTextStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    ),
    optionText: String,
    onClickOption: () -> Unit
) {

    var optionButtonState by rememberSaveable { mutableStateOf(OptionButtonState.UNSELECTED) }

    val textColor by animateColorAsState(
        targetValue = when (optionButtonState) {
            OptionButtonState.CORRECT -> Color.White
            OptionButtonState.INCORRECT -> Color.White
            OptionButtonState.UNSELECTED -> Neutral800
        }, label = ""
    )

    val backgroundColor by animateColorAsState(
        targetValue = when (optionButtonState) {
            OptionButtonState.CORRECT -> Success50
            OptionButtonState.INCORRECT -> Error50
            OptionButtonState.UNSELECTED -> Primary50.copy(0.2f)
        },
        label = ""
    )
    val mainColor by animateColorAsState(
        targetValue = when (optionButtonState) {
            OptionButtonState.CORRECT -> Success500
            OptionButtonState.INCORRECT -> Error500
            OptionButtonState.UNSELECTED -> Primary50
        }, label = ""
    )

    LaunchedEffect(isSelected) {
        if (isSelected) {
            optionButtonState = if (isCorrectAnswer) OptionButtonState.CORRECT else OptionButtonState.INCORRECT
        }
    }

    PressableContent(
        modifier = modifier,
        corners = 24.dp,
        borderWidth = (1.5).dp,
        enabled = true,
        onClick = {
            onClickOption.invoke()
        },
        borderColor = backgroundColor,
        containerColor = mainColor
    ) {
        Box (
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                text = optionText,
                color = textColor,
                style = optionTextStyle,
                maxLines = 4
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuizOptionButtonPrev() {
    Row (
        modifier = Modifier.fillMaxWidth()
    ) {
        QuizOptionEditableButton(
            modifier = Modifier.width(100.dp)
                .height(100.dp),
            optionText = "",
            onOptionEntered = {},
            placeholderText = "A"
        ) {

        }
        Spacer(modifier = Modifier.width(100.dp))
        QuizOptionEditableButton(
            modifier = Modifier.weight(1f)
                .height(100.dp),
            optionText = "",
            onOptionEntered = {},
            placeholderText = "Option B"
        ) {

        }
    }
}