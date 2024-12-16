package com.example.deezermusicapplication.presentation.screens.quiz_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deezermusicapplication.presentation.components.button.QuizOptionGameButton
import com.example.deezermusicapplication.presentation.screens.add_question_screen.QuestionTitleField

@Composable
fun QuizScreen(
    allGames: List<GameModel>,
    currentGameIndex: Int,
    currentGame: GameModel?,
    isQuizFinished: Boolean,
    correctQuestionCount: Int,
    onOptionSelected: (optionIndex: Int) -> Unit,
    onBackPressed: () -> Unit
) {
    if (currentGame != null)
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
                        text = if (isQuizFinished.not()) "Question ${currentGameIndex + 1}" else "Quiz over!",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                }
            }
        ) {
            if (isQuizFinished.not()) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 24.dp)
                ) {
                    QuestionTitleField(
                        modifier = Modifier.fillMaxWidth(),
                        value = currentGame.quiz.question,
                        onValueChanged = {},
                        isReadOnly = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

//                    val pagerState = rememberPagerState { allGames.size }
//                    LaunchedEffect(currentGameIndex) {
//                        pagerState.animateScrollToPage(currentGameIndex)
//                    }
                    AnimatedContent(
                        targetState = currentGame
//                        state = pagerState,
//                        userScrollEnabled = false
                    ) { game->
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                QuizOptionGameButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(100.dp),
                                    optionText = game.quiz.optionA,
                                    isSelected = game.selectedOptionIndex == 1,
                                    isCorrectAnswer = game.quiz.correctOptionIndex == 1,
                                    onClickOption = {
                                        onOptionSelected(1)
                                    }
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                QuizOptionGameButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(100.dp),
                                    optionText = game.quiz.optionB,
                                    isSelected = game.selectedOptionIndex == 2,
                                    isCorrectAnswer = game.quiz.correctOptionIndex == 2,
                                    onClickOption = {
                                        onOptionSelected(2)
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                QuizOptionGameButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(100.dp),
                                    optionText = game.quiz.optionC,
                                    isSelected = game.selectedOptionIndex == 3,
                                    isCorrectAnswer = game.quiz.correctOptionIndex == 3,
                                    onClickOption = {
                                        onOptionSelected(3)
                                    }
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                QuizOptionGameButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(100.dp),
                                    optionText = game.quiz.optionD,
                                    isSelected = game.selectedOptionIndex == 4,
                                    isCorrectAnswer = game.quiz.correctOptionIndex == 4,
                                    onClickOption = {
                                        onOptionSelected(4)
                                    }
                                )
                            }
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Quiz is finished!",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Your result: $correctQuestionCount/${allGames.size}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
}

@Preview
@Composable
private fun QuizScreenPrev() {
    QuizScreen(
        allGames = listOf(),
        currentGameIndex = 8900,
        currentGame = null,
        isQuizFinished = false,
        correctQuestionCount = 5957,
        onOptionSelected = {},
        onBackPressed = {}

    )
}