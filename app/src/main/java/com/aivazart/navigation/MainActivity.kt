package com.aivazart.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.aivazart.navigation.model.ProductDatabase
import com.aivazart.navigation.view.ProteinTabs
import com.aivazart.navigation.ui.theme.NavigationTheme
import com.aivazart.navigation.view.ExerciseScreen
import com.aivazart.navigation.view.MainScaffold
import com.aivazart.navigation.view.ProteinScreen
import com.aivazart.navigation.view.getBottomNavigationItems
import com.aivazart.navigation.viewmodel.ProductViewModel

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ProductDatabase::class.java,
            "products.db"
        ).build()
    }

    private val viewModel by viewModels<ProductViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductViewModel(db.dao) as T
                }
            }
        }
    )

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
        val navController = rememberNavController()
        val items = getBottomNavigationItems()
        MainScaffold(navController, items, viewModel)
    }
}
