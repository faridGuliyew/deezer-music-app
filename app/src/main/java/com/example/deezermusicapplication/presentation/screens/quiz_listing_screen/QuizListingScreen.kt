package com.example.deezermusicapplication.presentation.screens.quiz_listing_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deezermusicapplication.R
import com.example.deezermusicapplication.data.local.database.entity.QuizEntity
import com.example.deezermusicapplication.data_models.SearchType
import com.example.deezermusicapplication.presentation.screens.add_question_screen.QuestionTitleField
import com.example.deezermusicapplication.presentation.screens.search_screen.AlbumSearchItem
import com.example.deezermusicapplication.presentation.screens.search_screen.SearchField
import com.example.deezermusicapplication.presentation.screens.search_screen.TrackSearchItem
import com.example.deezermusicapplication.utils.extension.defaultScreenPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizListingScreen(
    allQuizzes: List<QuizEntity>,
    onRemoveQuestion: (Int) -> Unit,
    onPlayPreview: (String) -> Unit,
    onBackPressed: () -> Unit,
    onPlayQuiz: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .defaultScreenPadding()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
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
                modifier = Modifier.weight(1f),
                text = "Related quizzes",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            )
            IconButton(
                modifier = Modifier,
                onClick = {
                    onPlayQuiz()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "play quiz"
                )
            }
        }
        if (allQuizzes.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.40f))
                Text(
                    text = "Not seeing any questions?",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Start adding your questions by pressing '+' icon to the right of any music you want!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(items = allQuizzes, key = { _,item-> item.questionId }) { index, quiz ->

                    SwipeToDismissBox(
                        modifier = Modifier.animateItem(),
                        state = rememberSwipeToDismissBoxState(
                            confirmValueChange = {
                                if (it == SwipeToDismissBoxValue.EndToStart) {
                                    onRemoveQuestion(quiz.questionId)
                                }
                                true
                            },
                            positionalThreshold = {
                                it / 2
                            }
                        ),
                        backgroundContent = {
                            Row(modifier = Modifier.fillMaxSize().background(color = Color.Red)) {}
                        },
                        content = {
                            Row (
                                modifier = Modifier.background(color = Color.White),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                QuizItem(
                                    question = "${index+1}.  ${quiz.question}",
                                    onClickPlayPreview = {
                                        onPlayPreview(quiz.trackId)
                                    }
                                )
                            }
                        },
                        enableDismissFromStartToEnd = false
                    )
                }
            }
        }
    }
}

@Composable
fun QuizItem(question: String, onClickPlayPreview: () -> Unit) {
    Text(
        modifier = Modifier.fillMaxWidth()
            .background(
                color = Color(0xFFE6E0E9)
            )
            .clickable {
                onClickPlayPreview()
            }
            .padding(16.dp),
        text = question
    )
}

@Preview (showBackground = true)
@Composable
private fun QuizListingScreenPrev() {
    QuizListingScreen(
        allQuizzes = listOf(
            QuizEntity(
                questionId = 6269,
                playlistId = 2428,
                trackId = "aeque",
                trackPreview = "solet",
                question = "Which song was it?",
                optionA = "disputationi",
                optionB = "quis",
                optionC = "dolore",
                optionD = "fringilla",
                correctOptionIndex = 5281

            ),
        ),
        onRemoveQuestion = {},
        onPlayPreview = {}, onBackPressed = {}, onPlayQuiz = {}
    )
}