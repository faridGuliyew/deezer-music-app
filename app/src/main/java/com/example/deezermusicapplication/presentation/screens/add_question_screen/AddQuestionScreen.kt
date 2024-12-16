package com.example.deezermusicapplication.presentation.screens.add_question_screen

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deezermusicapplication.presentation.components.button.QuizOptionEditableButton
import com.example.deezermusicapplication.presentation.components.button.Success500

@Composable
fun AddQuestionScreen(
    playlistName: String,
    onBackPressed: () -> Unit,
    onAddQuestion: (
        question: String,
        optionA: String,
        optionB: String,
        optionC: String,
        optionD: String,
        correctOptionIndex: Int
    ) -> Unit
) {
    var question by rememberSaveable { mutableStateOf("") }
    var correctOptionIndex by rememberSaveable { mutableIntStateOf(-1) }
    var optionA by rememberSaveable {
        println("HERE!")
        mutableStateOf("")
    }
    var optionB by rememberSaveable { mutableStateOf("") }
    var optionC by rememberSaveable { mutableStateOf("") }
    var optionD by rememberSaveable { mutableStateOf("") }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp),
            ) {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        onBackPressed()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "go back"
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Quiz: $playlistName",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp)
                    .padding(bottom = 48.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                if (
                    optionA.isNotEmpty() && optionB.isNotEmpty() && optionC.isNotEmpty() && optionD.isNotEmpty()
                    && correctOptionIndex != -1
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Success500
                        ),
                        onClick = {
                            onAddQuestion(question, optionA, optionB, optionC, optionD, correctOptionIndex)
                        }
                    ) {
                        Text(
                            text = "Save question"
                        )
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
        ) {
            QuestionTitleField(
                modifier = Modifier.fillMaxWidth(),
                value = question,
                onValueChanged = {
                    question = it
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    QuizOptionEditableButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        optionText = optionA,
                        onOptionEntered = { optionA = it },
                        placeholderText = "Option A",
                        shouldHighlightButton = correctOptionIndex == 1,
                        onClickOption = { correctOptionIndex = 1 }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    QuizOptionEditableButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        optionText = optionB,
                        onOptionEntered = { optionB = it },
                        placeholderText = "Option B",
                        shouldHighlightButton = correctOptionIndex == 2,
                        onClickOption = { correctOptionIndex = 2 }
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    QuizOptionEditableButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        optionText = optionC,
                        onOptionEntered = { optionC = it },
                        placeholderText = "Option C",
                        shouldHighlightButton = correctOptionIndex == 3,
                        onClickOption = { correctOptionIndex = 3 }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    QuizOptionEditableButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        optionText = optionD,
                        onOptionEntered = { optionD = it },
                        placeholderText = "Option D",
                        shouldHighlightButton = correctOptionIndex == 4,
                        onClickOption = { correctOptionIndex = 4 }
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionTitleField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    endIcon: ImageVector? = null,
    isReadOnly: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        shape = RoundedCornerShape(12.dp),
        readOnly = isReadOnly,
        placeholder = {
            Text(
                text = "Question goes here...",
                style = textStyle,
                // FIXME - THEME
                color = Color.Black.copy(0.3f)
            )
        },
        textStyle = textStyle,
        // removed indicator, by making it transparent
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        trailingIcon = {
            endIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "end"
                )
            }
        },
        keyboardActions = KeyboardActions(onDone = {
            // hide keyboard on ime action
            focusManager.clearFocus()
        }),
        maxLines = 3
    )
}

@Preview(showBackground = true)
@Composable
private fun AddQuestionScreenPrev() {
    AddQuestionScreen(
        playlistName = "Happy vibes",
        onBackPressed = {}, onAddQuestion = {_,_,_,_,_,_->}
    )
}