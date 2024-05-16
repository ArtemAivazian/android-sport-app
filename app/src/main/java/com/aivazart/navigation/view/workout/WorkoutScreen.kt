package com.aivazart.navigation.view.workout

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.aivazart.navigation.model.ProductEvent
import com.aivazart.navigation.model.Workout
import com.aivazart.navigation.model.WorkoutEvent
import com.aivazart.navigation.model.WorkoutState
import com.aivazart.navigation.view.exercise.screens.RequestState
import com.aivazart.navigation.view.protein.AddProductDialog
import com.aivazart.navigation.viewmodel.WorkoutViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(viewModel: WorkoutViewModel,
                  navController: NavHostController,
                  state: WorkoutState,
                  onEvent: (WorkoutEvent) -> Unit) {

//    LaunchedEffect(Unit) {
//        viewModel.getWorkouts()
//    }
//    val workouts by viewModel.workouts.collectAsState()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Workouts",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(thickness = 1.dp, color = Color.White)
            }

        },

        content = { padding ->
            WorkoutExercisesList(
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
                workouts = state.workouts,
                navController
            )
        },
            floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(WorkoutEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add product"
                )
            }
    },

    )
    if(state.isAddingWorkout) {
        AddWorkoutDialog(state = state, onEvent = onEvent)
    }
}

@Composable
fun WorkoutExercisesList(
    modifier: Modifier = Modifier,
    workouts: List<Workout>,
    navController: NavHostController) {

    var loadedWorkouts: List<Workout> = emptyList()

//    if (workouts is RequestState.Success) {
//        loadedWorkouts = workouts.data
//    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(80.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)) {
        items(workouts) { item ->
            WorkoutListItem(item) {
//                navController.navigate("ExerciseDetails/${item.exerciseId}")
            }
        }
    }

}
@Composable
fun WorkoutListItem(item: Workout, onItemClick: () -> Unit) {
    Row (
        Modifier.clickable(onClick = onItemClick).fillMaxWidth().padding(horizontal = 25.dp)
    ) {
        Spacer(
            modifier = Modifier.size(5.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = item.name,
                fontSize = 20.sp
            )
        }
    }
}