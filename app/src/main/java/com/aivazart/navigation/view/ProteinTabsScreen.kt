package com.aivazart.navigation.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


data class TabItem(
    val title: String,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProteinTabs() {
    val navController = rememberNavController()
    //TABS
    val tabItems = listOf(
        TabItem(title = "Add", route = "add"),
        TabItem(title = "Review", route = "review")
    )

    // Use a state to track the selected tab index
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Observe navigation changes and update tab selection
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val index = tabItems.indexOfFirst { it.route == destination.route }
            if (index >= 0) selectedTabIndex = index
        }
    }

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            if (navController.currentDestination?.route != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        },
                        text = { Text(item.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavigationGraph(navController, tabItems, innerPadding)
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, tabItems: List<TabItem>, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = tabItems.first().route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(tabItems[0].route) { AddProductScreen() }
        composable(tabItems[1].route) { ReviewScreen() }
    }
}