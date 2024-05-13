package com.aivazart.navigation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        TabItem(title = "Progress", route = "progress"),
        TabItem(title = "Products", route = "products")
    )
    var selectedTabIndex by remember { mutableStateOf(0) }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val index = tabItems.indexOfFirst { it.route == destination.route }
            if (index >= 0) selectedTabIndex = index
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Tracker",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(thickness = 1.dp, color = Color.White)
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .height(3.dp)
                                .background(color = Color.Green),
                            color = Color.Transparent
                        )
                    },
                    containerColor = Color.Black,
                    contentColor = Color.White
                ) {
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
                            text = {
                                Text(
                                    text = item.title,
                                    color = if (index == selectedTabIndex) Color.White else Color.Gray,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        )
                    }
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

