package com.aivazart.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.outlined.AccessibilityNew
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aivazart.navigation.view.ProteinTabs
import com.aivazart.navigation.ui.theme.NavigationTheme
import com.aivazart.navigation.view.ExerciseScreen
import com.aivazart.navigation.view.ProteinScreen

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val items = listOf(
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
                    var selectedItemIndex by rememberSaveable {
                        mutableStateOf(0)
                    }
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Scaffold(
                            bottomBar = {
                                NavigationBar {
                                    items.forEachIndexed { index, item ->
                                        NavigationBarItem(
                                            selected = selectedItemIndex == index,
                                            onClick = {
                                                selectedItemIndex = index
                                                 navController.navigate(item.title)
                                            },
                                            label = { Text(text = item.title) },
                                            alwaysShowLabel = false,
                                            icon = {
                                                Icon(
                                                    imageVector = if (index == selectedItemIndex) {
                                                        item.selectedIcon
                                                    } else item.unselectedIcon,
                                                    contentDescription = item.title
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        ) { paddingValues ->
                            Box(modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize()) {
                                NavHost(navController = navController, startDestination = "Exercise") {
                                    composable("Exercise") { ExerciseScreen() }
                                    composable("Tracker") { ProteinScreen() }  // Assuming you want to use ProteinScreen for "Tracker"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
