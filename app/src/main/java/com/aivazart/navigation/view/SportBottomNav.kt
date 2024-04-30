package com.aivazart.navigation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.outlined.AccessibilityNew
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aivazart.navigation.BottomNavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavHostController,
    items: List<BottomNavigationItem>
) {
    val selectedItemIndex = items.indexOfFirst { it.title == navController.currentDestination?.route }
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            val route = item.title
                            if (navController.currentDestination?.route != route) {
                                navController.navigate(route) {
                                    // Avoid multiple copies of the same destination
                                    launchSingleTop = true
                                    // Optional: Clear back stack when selecting items
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            }
                        },
                        label = { Text(item.title) },
                        alwaysShowLabel = false,
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavigationGraph(navController, innerPadding)
    }
}


@Composable
fun NavigationGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navController, startDestination = "Exercise", modifier = Modifier.padding(paddingValues)) {
        composable("Exercise") { ExerciseScreen() }
        composable("Tracker") { ProteinScreen() }
    }
}

fun getBottomNavigationItems(): List<BottomNavigationItem> = listOf(
    BottomNavigationItem(
        title = "Exercise",
        selectedIcon = Icons.Filled.AccessibilityNew,
        unselectedIcon = Icons.Outlined.AccessibilityNew
    ),
    BottomNavigationItem(
        title = "Tracker",
        selectedIcon = Icons.Filled.TrackChanges,
        unselectedIcon = Icons.Outlined.TrackChanges
    )
)