package com.example.algorithmvisualiser

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.algorithmvisualiser.searchAlgorithms.SearchViewModel



val BREADTH_FIRST_SEARCH = "Breadth First Search"
val GREEDY_BEST_FIRST_SEARCH = "Greedy Best First Search"
val DEPTH_FIRST_SEARCH = "Depth First Search"
val ASTAR_SEARCH = "A* search"








@Composable
fun NodesMap(searchViewModel: SearchViewModel) {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }


    Column(
        modifier = Modifier

            .size(900.dp)


            .onSizeChanged { newSize ->
                size = newSize
                //Log.i("test", size.toString())

            }
    ) {
        
        


        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        //Log.i("test", screenWidth.value.toString())

        if(searchViewModel.squareList.size == 0){
            Log.i("test", size.toSize().toString())
            searchViewModel.insertNodes(screenWidth.value.toInt(),screenHeight.value.toInt())


        }


        searchViewModel.squareList.forEach{ squares ->


            Row(
                modifier = Modifier



            ){

                squares.forEach {square ->
                    //Log.i("test", node.isWall.toString())

                    NodeSquare(square = square, searchViewModel =searchViewModel )

                }

            }


        }
        
    }
}

@Composable
fun NodeSquare(
    square: Square,
    searchViewModel: SearchViewModel
){


    Box(
        modifier = Modifier
            .size(
                square.size.width.dp, square.size.height.dp
            )


            .background(
                color = if(square.squareType.value == SquareTypes.Wall){
                    Color.Black
                }else if(square.squareType.value == SquareTypes.Goal){
                    Color(0xff40A2E3)
                }else if(square.squareType.value == SquareTypes.Start){
                    Color(0xff0D9276)
                }else if (square.squareType.value == SquareTypes.IsInFrontier){
                    Color(0xff496989)
                }else if(square.squareType.value == SquareTypes.IsToExplore||square.squareType.value == SquareTypes.IsInPath){
                    Color(0xFFE7CD2A)

                }
                else{
                    Color.White
                }
            )
            .border(
                width = 2.dp,
                color = Color(0xffBBE2EC)
            )
            .clickable {


//                searchViewModel.apply {
//
//                    if(selectedNodeType.value == SquareTypes.Goal){
//
//                        if(currentGoal.value != null){
//                            if (square.position != currentGoal.value!!.position){
//                                squareList[currentGoal.value!!.position.first][currentGoal.value!!.position.second].squareType.value = SquareTypes.Air
//
//                            }
//                        }
//                        currentGoal.value = square
//                        square.squareType.value = SquareTypes.Goal
//
//                    }else if(selectedNodeType.value == SquareTypes.Start){
//                        if(currentStart.value != null){
//                            if (square.position != currentStart.value!!.position){
//                                squareList[currentStart.value!!.position.first][currentStart.value!!.position.second].squareType.value = SquareTypes.Air
//
//                            }
//                        }
//                        currentStart.value = square
//                        square.squareType.value = SquareTypes.Start
//
//                    } else{
//
//                        if(square.squareType.value != SquareTypes.Goal && square.squareType.value != SquareTypes.Start){
//                            square.squareType.value = selectedNodeType.value
//
//                        }
//
//                    }
//
//
//                }


            }

    )

}