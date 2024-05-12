package com.example.algorithmvisualiser

data class Node(


    val state: Pair<Int, Int>,
    var parent:Node? = null,
    var pathCost:Int = 0



)






