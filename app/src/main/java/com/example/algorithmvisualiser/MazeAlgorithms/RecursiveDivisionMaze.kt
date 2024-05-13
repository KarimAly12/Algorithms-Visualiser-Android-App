package com.example.algorithmvisualiser.MazeAlgorithms

import android.icu.text.ListFormatter.Width
import android.util.Log
import com.example.algorithmvisualiser.Square
import com.example.algorithmvisualiser.SquareTypes
import kotlinx.coroutines.delay
import kotlin.random.Random



enum class Orientation{
    HORIZONTAL,
    VERTICAL
}


val HORIZONTAL = Orientation.HORIZONTAL
val VERTICAL = Orientation.VERTICAL






suspend fun RecusiveDivision(squareList: List<List<Square>>, x: Int, y: Int , width: Int , height: Int ){

    if(width <= 2 || height  <= 2) return

    //if(y + 1 == height-2 || x+1 == width -2 || y+1 > height-2 || x+1 > width-2) return


    val orientation = if(height > width) Orientation.HORIZONTAL else Orientation.VERTICAL




    //Log.i("test", width.toString() + " " + height.toString()+ " " + y.toString() + " "+ x.toString())
    val wp = if(orientation == Orientation.HORIZONTAL) y +  Random.nextInt( height-2 ) else x + Random.nextInt(if (width ==2) 1 else width-2)

    val length = if(orientation == Orientation.HORIZONTAL) width else height

    if(orientation == Orientation.HORIZONTAL){
        for( i in x.. width){
            squareList[wp][i].squareType.value = SquareTypes.Wall

            delay(50)
        }
    }else{
        for( i in y .. height){
            squareList[i][wp].squareType.value = SquareTypes.Wall
            delay(50)
        }

    }

    var nx = x
    var ny = y
    var nw = if(orientation == Orientation.HORIZONTAL) width else wp-x
    var nh = if(orientation == Orientation.HORIZONTAL) wp- y else height

    RecusiveDivision(squareList, nx, ny, nw, nh)


    nx = if(orientation == Orientation.HORIZONTAL) x else wp+1
    ny = if (orientation == Orientation.HORIZONTAL) wp+1 else y
    nw = if(orientation == Orientation.HORIZONTAL) width else (width-wp)
    nh = if(orientation == Orientation.HORIZONTAL) (height-wp) else height

    RecusiveDivision(squareList, nx, ny, nw, nh)



}