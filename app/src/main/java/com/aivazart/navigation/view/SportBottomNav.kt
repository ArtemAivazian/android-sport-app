package com.aivazart.navigation.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.outlined.AccessibilityNew
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SportsGymnastics
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aivazart.navigation.BottomNavigationItem
import com.aivazart.navigation.view.exercise.ExerciseScreen
import com.aivazart.navigation.view.exercise.screens.CardioScreen
import com.aivazart.navigation.view.exercise.screens.ExerciseDetailsScreen
import com.aivazart.navigation.view.exercise.screens.StrengthScreen
import com.aivazart.navigation.view.workout.WorkoutScreen
import com.aivazart.navigation.view.protein.ProteinScreen
import com.aivazart.navigation.view.settings.BodyStatsScreen
import com.aivazart.navigation.view.workout.ChooseExercisesScreen
import com.aivazart.navigation.view.workout.DisplayExerciseScreen
import com.aivazart.navigation.viewmodel.BodyStatsViewModel
import com.aivazart.navigation.viewmodel.ExerciseViewModel
import com.aivazart.navigation.viewmodel.ProductViewModel
import com.aivazart.navigation.viewmodel.WorkoutViewModel

@Composable
fun MainScaffold(
    navController: NavHostController,
    items: List<BottomNavigationItem>,
    productViewModel: ProductViewModel,
    exerciseViewModel: ExerciseViewModel,
    workoutViewModel: WorkoutViewModel,
    bodyStatsViewModel: BodyStatsViewModel

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
                                        saveState = false
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
        NavigationGraph(
            navController,
            innerPadding,
            productViewModel,
            exerciseViewModel,
            workoutViewModel,
            bodyStatsViewModel
        )
    }
}


@Composable
fun NavigationGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    productViewModel: ProductViewModel,
    exerciseViewModel: ExerciseViewModel,
    workoutViewModel: WorkoutViewModel,
    bodyStatsViewModel: BodyStatsViewModel
) {
    val state by workoutViewModel.state.collectAsState()
    NavHost(navController = navController, startDestination = "Exercise", modifier = Modifier.padding(paddingValues)) {
        composable("Exercise") {   ExerciseScreen(exerciseViewModel, navController) }
        composable("Workouts") {   WorkoutScreen(workoutViewModel, navController, state, workoutViewModel::onEvent) }
        composable("Tracker") { ProteinScreen(productViewModel, bodyStatsViewModel) }
        composable("Settings") {
            val state by bodyStatsViewModel.state.collectAsState()
            state?.let { it1 -> BodyStatsScreen(state = it1, onEvent = bodyStatsViewModel::onEvent) }
        }
        composable("Strength") { StrengthScreen(exerciseViewModel, navController) }
        composable("Cardio") { CardioScreen(exerciseViewModel, navController) }
        composable("Stretch") { StrengthScreen(exerciseViewModel, navController) }
        composable("ChooseExercisesScreen" + "/{name}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                    nullable = false
                }
            )) { entry ->
            entry.arguments!!.getString("name")?.let {
                ChooseExercisesScreen(name = it,
                    exerciseViewModel, workoutViewModel, navController)
            }
        }
        composable("DisplayExercisesScreen" + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                    nullable = false
                }
            )) { entry ->
            DisplayExerciseScreen(id = entry.arguments!!.getInt("id"),
                exerciseViewModel, workoutViewModel, navController)
        }
        composable("ExerciseDetails" + "/{exercise}",
            arguments = listOf(
                navArgument("exercise"){
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { entry -> ExerciseDetailsScreen(exercise = entry.arguments!!.getInt("exercise"),
            viewModel = exerciseViewModel, navController = navController
        ) }

    }
}

fun getBottomNavigationItems(): List<BottomNavigationItem> = listOf(
    BottomNavigationItem(
        title = "Exercise",
        selectedIcon = Icons.Filled.AccessibilityNew,
        unselectedIcon = Icons.Outlined.AccessibilityNew
    ),
    BottomNavigationItem(
        title = "Workouts",
        selectedIcon = Icons.Filled.SportsGymnastics,
        unselectedIcon = Icons.Outlined.SportsGymnastics
    ),
    BottomNavigationItem(
        title = "Tracker",
        selectedIcon = Icons.Filled.TrackChanges,
        unselectedIcon = Icons.Outlined.TrackChanges
    ),
    BottomNavigationItem(
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
)