package com.aivazart.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.aivazart.navigation.model.ProductDatabase
import com.aivazart.navigation.ui.theme.NavigationTheme
import com.aivazart.navigation.view.MainScaffold
import com.aivazart.navigation.view.getBottomNavigationItems
import com.aivazart.navigation.viewmodel.ExerciseViewModel
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
        ).fallbackToDestructiveMigration()
        .build()
    }

    private val productViewModel by viewModels<ProductViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductViewModel(db.productDao) as T
                }
            }
        }
    )

    private val exerciseViewModel by viewModels<ExerciseViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ExerciseViewModel(db.exerciseDao) as T
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
        MainScaffold(navController, items, productViewModel, exerciseViewModel  )
    }
}
