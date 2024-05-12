package com.example.algorithmvisualiser

sealed class SquareTypes(val name:String){

    object Air:SquareTypes("Air")
    object Start:SquareTypes("Start")
    object Goal:SquareTypes("Target")
    object Wall:SquareTypes("Wall")
    object IsInFrontier:SquareTypes("")
    object IsToExplore:SquareTypes("")
    object IsInPath:SquareTypes("")
}
