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
import com.aivazart.navigation.view.MainScaffold
import com.aivazart.navigation.view.ProteinScreen
import com.aivazart.navigation.view.getBottomNavigationItems

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                    }
                }
            }
    }


    @Composable
    fun AppContent() {
//        val navController = rememberNavController()
//        val items = getBottomNavigationItems()
//        var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
//
//        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//            MainScaffold(navController, items, selectedItemIndex) { index ->
//                selectedItemIndex = index
//                navController.navigate(items[index].title)
//            }
//        }
        val navController = rememberNavController()
        val items = getBottomNavigationItems()
        MainScaffold(navController, items)
    }
}
