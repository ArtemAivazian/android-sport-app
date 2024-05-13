package com.aivazart.navigation.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aivazart.navigation.view.protein.ProgressBarScreen
import com.aivazart.navigation.view.protein.ReviewScreen
import com.aivazart.navigation.viewmodel.BodyStatsViewModel
import com.aivazart.navigation.viewmodel.ProductViewModel


data class TabItem(
    val title: String,
    val route: String
)

@Composable
fun ProteinTabs(
    productViewModel: ProductViewModel,
    bodyStatsViewModel: BodyStatsViewModel
) {
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
        NavigationGraph(
            navController,
            tabItems,
            innerPadding,
            productViewModel,
            bodyStatsViewModel
        )
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    tabItems: List<TabItem>,
    paddingValues: PaddingValues,
    productViewModel: ProductViewModel,
    bodyStatsViewModel: BodyStatsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = tabItems.first().route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(tabItems[0].route) {
            ProgressBarScreen(
                productViewModel,
                bodyStatsViewModel
            )
        }
        composable(tabItems[1].route) {
            val state by productViewModel.state.collectAsState()
            ReviewScreen(state = state, onEvent = productViewModel::onEvent)
        }
    }
}

