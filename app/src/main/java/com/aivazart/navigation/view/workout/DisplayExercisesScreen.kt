package com.aivazart.navigation.view.workout

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.aivazart.navigation.model.Exercise
import com.aivazart.navigation.view.exercise.screens.RequestState
import com.aivazart.navigation.viewmodel.ExerciseViewModel
import com.aivazart.navigation.viewmodel.WorkoutViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayExerciseScreen(id:Int, exerciseViewModel: ExerciseViewModel,workoutViewModel: WorkoutViewModel, navController: NavHostController) {

    LaunchedEffect(Unit) {
        val workout = workoutViewModel.getWorkout(id)
        exerciseViewModel.getExercisesForWorkout(workout.listOfExercisesIds)
    }

    val exercises by exerciseViewModel.exercisesForWorkout.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Exercises", overflow = TextOverflow.Ellipsis)

                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },

                )

        },


        content = { padding ->
            CardioExercisesList(
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
                exercises = exercises,
                navController
            )
        }
    )

}

@Composable
fun CardioExercisesList(
    modifier: Modifier = Modifier,
    exercises: RequestState<List<Exercise>>,
    navController: NavHostController) {

    var loadedExercises: List<Exercise> = emptyList()

    if (exercises is RequestState.Success) {
        loadedExercises = exercises.data
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(80.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)) {
        items(loadedExercises) { item ->
            CardioListItem(item) {
                navController.navigate("ExerciseDetails/${item.exerciseId}")
            }

        }
    }

}
@Composable
fun CardioListItem(item: Exercise, onItemClick: () -> Unit) {
    Row (
        Modifier
            .clickable(onClick = onItemClick)
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
    ) {
        AsyncImage(
            model = item.imageUri,
            contentDescription = "Product Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(20.dp)
        )
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

//            Text(text = item.name)
    }

}