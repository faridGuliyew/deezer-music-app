package com.example.deezermusicapplication.utils.extension

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.defaultScreenPadding() = then(
    Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
)