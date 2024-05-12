package com.example.algorithmvisualiser.searchAlgorithms

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.algorithmvisualiser.BREADTH_FIRST_SEARCH
import com.example.algorithmvisualiser.Node
import com.example.algorithmvisualiser.SquareTypes
import com.example.algorithmvisualiser.Square
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.PriorityQueue
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.absoluteValue


@HiltViewModel
class SearchViewModel @Inject constructor():ViewModel()  {

    val squareList = mutableStateListOf<SnapshotStateList<Square>>()
    var selectedNodeType = mutableStateOf<SquareTypes>(SquareTypes.Air)
    var currentGoal = mutableStateOf<Square?>(null)
    var currentStart = mutableStateOf<Square?>(null)

    var selectedSearchType = mutableStateOf(BREADTH_FIRST_SEARCH)

    var searchStarted = false


    fun insertNodes(width:Int, height:Int){

        val wid = calculateSquareWidth(width, height)
        val hei = calculateSquareHeight(height, height)

        for(i in 0..19){

            val list = mutableStateListOf<Square>()


            for (j in 0..11){

                if(i == 0 && j == 0){

                    currentStart.value = Square(
                        squareType = mutableStateOf(SquareTypes.Start),
                        size = IntSize(wid, hei) ,
                        position = Pair(i,j)
                    )

                    list.add(currentStart.value!!)
                }else if(i == 15 && j == 7){
                    currentGoal.value = Square(
                        squareType = mutableStateOf(SquareTypes.Goal),
                        size = IntSize(wid, hei) ,
                        position = Pair(i,j)
                    )


                    list.add(currentGoal.value!!)
                }else{

                    list.add(

                        Square(
                            size = IntSize(wid, hei),
                            position = Pair(i, j)
                        )

                    )

                }

            }

            squareList.add(list)

        }


    }

    fun reset(){

        squareList[currentGoal.value!!.position.first][currentGoal.value!!.position.second].squareType.value = SquareTypes.Goal
        for(i in 0..19){

            for (j in 0..11){

                if ((squareList[i][j].squareType.value !=SquareTypes.Wall) &&  (squareList[i][j].squareType.value !=SquareTypes.Goal) && (squareList[i][j].squareType.value !=SquareTypes.Start) ){
                    squareList[i][j].squareType.value = SquareTypes.Air

                }
            }

        }
    }


    fun breadthFirstSearch(){

        var currentNode = Node(
           state = currentStart.value!!.position
        )
        val queue = mutableListOf(currentNode)
        val reached = mutableListOf(currentNode.state)

        viewModelScope.launch {

            while (queue.isNotEmpty()){

                currentNode = queue.removeFirst()


                
                val expandedNodes = expand(currentNode)
                for (child in expandedNodes){
                    val state = child.state

                    if (state == currentGoal.value!!.position){
                        drawPath(child)
                        searchStarted = false
                        cancel()
                    }
                    if (!reached.contains(state)){

                        reached.add(child.state)
                        queue.add(child)
                        squareList[state.first][state.second].squareType.value = SquareTypes.IsToExplore
                        delay(35)
                        squareList[state.first][state.second].squareType.value = SquareTypes.IsInFrontier

                    }

                    delay(20)

                }
            }
        }
    }



    fun depthFirstSearch(){

        var currentNode = Node(
            state = currentStart.value!!.position
        )
        val queue = mutableListOf(currentNode)
        val reached = mutableListOf(currentNode.state)


        viewModelScope.launch {

            while (queue.isNotEmpty()){

                currentNode = queue.removeLast()



                val expandedNodes = expand(currentNode)
                for (child in expandedNodes){
                    val state = child.state
                    if (state == currentGoal.value!!.position){
                        drawPath(child)
                        searchStarted = false

                        cancel()
                    }
                    if (!reached.contains(state)){

                        reached.add(child.state)
                        queue.add(child)
                        squareList[state.first][state.second].squareType.value = SquareTypes.IsToExplore
                        delay(35)
                        squareList[state.first][state.second].squareType.value = SquareTypes.IsInFrontier

                    }

                    delay(20)

                }
            }
        }

    }


    fun greedyBestFirstSearch(){
        var currentNode = Node(
            state = currentStart.value!!.position
        )

        val priorityQueue = PriorityQueue<Node>(
            compareBy { manhattanDistance(it)  }
        )
        priorityQueue.add(currentNode)
        val reached = mutableListOf(currentNode.state)

        viewModelScope.launch {

            while (priorityQueue.isNotEmpty()){

                currentNode = priorityQueue.poll()!!
                Log.i("test", currentNode.state.toString())
                val expanded = expand(currentNode)
                for (child in expanded){

                    val state = child.state
                    if(state == currentGoal.value!!.position){
                        drawPath(child)
                        searchStarted = false
                        cancel()
                    }

                    if (!reached.contains(state)){

                        reached.add(child.state)
                        priorityQueue.add(child)
                        squareList[state.first][state.second].squareType.value = SquareTypes.IsToExplore
                        delay(35)
                        squareList[state.first][state.second].squareType.value = SquareTypes.IsInFrontier


                    }

                    delay(20)

                }
            }

        }



    }



    fun aStarSearch(){

        var currentNode = Node(
            state = currentStart.value!!.position,
            pathCost = 0
        )

        val priorityQueue = PriorityQueue<Node>(
            compareBy { (manhattanDistance(it) + it.pathCost)  }
        )
        priorityQueue.add(currentNode)

        val reached = mutableListOf(currentNode.state)

        viewModelScope.launch {

            while (priorityQueue.isNotEmpty()){

                currentNode = priorityQueue.poll()!!
                val expanded = expand(currentNode)
                for (child in expanded){

                    val state = child.state
                    if(state == currentGoal.value!!.position){
                        drawPath(child)
                        searchStarted = false
                        cancel()
                    }

                    if (!reached.contains(state)){

                        reached.add(child.state)
                        priorityQueue.add(child)
                        squareList[state.first][state.second].squareType.value = SquareTypes.IsToExplore
                        delay(35)
                        squareList[state.first][state.second].squareType.value = SquareTypes.IsInFrontier


                    }

                    delay(20)

                }
            }

        }



    }

    private suspend fun drawPath(node: Node){

        var currentNode = node
        val paths = mutableListOf<Pair<Int,Int>>()
        while (currentNode.parent != null){

            paths.add(currentNode.state)
            currentNode = currentNode.parent!!


        }

        for(i in paths.size-1 downTo 0){


            squareList[paths[i].first][paths[i].second].squareType.value = SquareTypes.IsInPath


            delay(30)

        }
    }



    private fun expand(node:Node): MutableList<Node> {
        val expandedNodes:MutableList<Node> = mutableListOf()

        //UP
        if((node.state.first != 0) && (squareList[node.state.first - 1][node.state.second].squareType.value != SquareTypes.Wall) ){

            val n = Node(
                state = Pair(node.state.first - 1, node.state.second),
                parent = node,
                pathCost = node.pathCost + 1
            )

            expandedNodes.add(n)
        }
        //DOWN
        if((node.state.first != squareList.size-1) &&  (squareList[node.state.first + 1][node.state.second].squareType.value != SquareTypes.Wall)){


            val n = Node(
                state = Pair(node.state.first + 1, node.state.second),
                parent = node,
                pathCost = node.pathCost + 1
            )
            expandedNodes.add(n)

        }
        //LEFT
        if(node.state.second != 0 && (squareList[node.state.first][node.state.second-1].squareType.value != SquareTypes.Wall)){

            val n = Node(
                state = Pair(node.state.first, node.state.second- 1),
                parent = node,
                pathCost = node.pathCost + 1
            )
            expandedNodes.add(n)

        }
        //RIGHT
        if((node.state.second != squareList[0].size-1) && (squareList[node.state.first][node.state.second+1].squareType.value != SquareTypes.Wall) ){

            val n = Node(
                state = Pair(node.state.first, node.state.second+ 1),
                parent = node,
                pathCost = node.pathCost + 1
            )
            expandedNodes.add(n)

        }


        return expandedNodes

    }


    private fun manhattanDistance(node: Node):Int{

        return abs(node.state.first - currentGoal.value!!.position.first) + abs(node.state.second - currentGoal.value!!.position.second)
    }

    private fun calculateSquareWidth(width:Int, height: Int):Int{
        return (width / 12).coerceAtMost(height / 20)

    }

    private fun calculateSquareHeight(width:Int, height: Int):Int{
        return (height / 20).coerceAtMost(width / 12)

    }
}