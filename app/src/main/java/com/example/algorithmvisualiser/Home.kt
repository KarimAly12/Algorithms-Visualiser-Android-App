package com.example.algorithmvisualiser

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.algorithmvisualiser.MazeAlgorithms.RecusiveDivision
import com.example.algorithmvisualiser.searchAlgorithms.SearchViewModel
import kotlinx.coroutines.launch


@Composable
fun NodesTypeDropMenu(searchViewModel: SearchViewModel){


    var expanded by remember {
        mutableStateOf(false)
    }

    val nodesTypes = listOf(SquareTypes.Air, SquareTypes.Start, SquareTypes.Goal, SquareTypes.Wall)
    //val disabledValue = "NodeTypes.Wall"
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    Box(
        modifier = Modifier

            .wrapContentSize(Alignment.TopStart)
    ){
        Text(
            text = nodesTypes[selectedIndex].name,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    expanded = true
                }
                .background(Color(0xffD9D9D9))
                .padding(8.dp)
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false},
            modifier = Modifier

                .background(Color(0xffD9D9D9))
        ) {
            nodesTypes.forEachIndexed {index, n ->
                DropdownMenuItem(text = {
                    Text(text = n.name)
                }, onClick = {

                    searchViewModel.selectedNodeType.value = n
                    selectedIndex = index
                    expanded = false
                })

            }

        }
    }


}


@Composable
fun SearchesListDropDownMenu(searchViewModel: SearchViewModel){


    var expanded by remember {
        mutableStateOf(false)
    }

    val searchTypes = listOf(BREADTH_FIRST_SEARCH, DEPTH_FIRST_SEARCH, GREEDY_BEST_FIRST_SEARCH, ASTAR_SEARCH)
    //val disabledValue = "NodeTypes.Wall"
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
    ){
        Text(
            text = searchTypes[selectedIndex],
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    expanded = true
                }
                .background(Color(0xffD9D9D9))
                .padding(8.dp)
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false},
            modifier = Modifier

                .background(Color(0xffD9D9D9))
        ) {
            searchTypes.forEachIndexed {index, s ->
                DropdownMenuItem(text = {
                    Text(text = s)
                }, onClick = {

                    searchViewModel.selectedSearchType.value = s
                    selectedIndex = index
                    expanded = false
                })

            }

        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(searchViewModel: SearchViewModel = hiltViewModel()){


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }

        }) {


        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {

                    scope.launch {
                        RecusiveDivision(searchViewModel.squareList, 0 , 0, 11, 19)

                    }
//                    if(!searchViewModel.searchStarted){
//                        searchViewModel.reset()
//
//                        searchViewModel.searchStarted = true
//
//                        if(searchViewModel.selectedSearchType.value == BREADTH_FIRST_SEARCH){
//                            searchViewModel.breadthFirstSearch()
//                        }else if(searchViewModel.selectedSearchType.value == GREEDY_BEST_FIRST_SEARCH){
//                            searchViewModel.greedyBestFirstSearch()
//                        }else if(searchViewModel.selectedSearchType.value == DEPTH_FIRST_SEARCH){
//                            searchViewModel.depthFirstSearch()
//                        }else if (searchViewModel.selectedSearchType.value == ASTAR_SEARCH){
//                            searchViewModel.aStarSearch()
//                        }
//
//
//                    }


                }) {

                    Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = "Visualise")

                }
            },
            topBar = {

                Row{
                    IconButton(onClick = {
                        scope.launch {
                            if(drawerState.isClosed) drawerState.open() else drawerState.close()
                        }
                    }) {
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu")
                    }

                    NodesTypeDropMenu(searchViewModel)

                    SearchesListDropDownMenu(searchViewModel)

                }

            }
        ) {

            Column(modifier = Modifier.padding(it)) {




                NodesMap(searchViewModel)



            }

        }






    }






}