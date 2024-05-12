package com.example.algorithmvisualiser

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize

class Square(
    val squareType:MutableState<SquareTypes> = mutableStateOf(SquareTypes.Air),
    val size: IntSize,
    val position: Pair<Int, Int>,
) {
}