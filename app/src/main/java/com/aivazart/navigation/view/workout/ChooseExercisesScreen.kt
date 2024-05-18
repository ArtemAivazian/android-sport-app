package com.aivazart.navigation.view.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.Alignment
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseExercisesScreen(name:String,
                          exerciseViewModel: ExerciseViewModel,
                          workoutViewModel: WorkoutViewModel,
                          navController: NavHostController) {

    LaunchedEffect(Unit) {
        exerciseViewModel.getExercises()
    }
    val exercises by exerciseViewModel.exercises.collectAsState()
    val selectedExerciseIds = remember { mutableSetOf<Int>() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Exercises", overflow = TextOverflow.Ellipsis)
                },

                actions = {
                    IconButton(onClick = {
                        val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
                        coroutineScope.launch {
                            val id = workoutViewModel.getWorkoutIdByName(name)
                            workoutViewModel.updateExerciseIdList(id, selectedExerciseIds.toList())
                            navController.navigate("Workouts")
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Choice"
                        )
                    }
                }
                )
        },
        content = { padding ->
            ExercisesList(
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
                exercises = exercises,
                navController = navController,
                selectedExerciseIds = selectedExerciseIds
            )
        }
    )
}

@Composable
fun ExercisesList(modifier: Modifier = Modifier,
                        exercises: RequestState<List<Exercise>>,
                        navController: NavHostController,
                        selectedExerciseIds: MutableSet<Int>) {

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
            ListItem(item, selectedExerciseIds) {}
        }
    }
}
@Composable
fun ListItem(item: Exercise, selectedExerciseIds: MutableSet<Int>, onItemClick: () -> Unit) {

    var checked by remember { mutableStateOf(false) }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onItemClick).fillMaxWidth().padding(horizontal = 25.dp)
    ) {
        AsyncImage(
            model = item.imageUri,
            contentDescription = "Exercise Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(20.dp)
        )
        Spacer(
            modifier = Modifier.size(5.dp)
        )
        Text(
            text = item.name,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier.size(5.dp)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = {checked = it
                if (checked) {
                    selectedExerciseIds.add(item.exerciseId)
                } else {
                    selectedExerciseIds.remove(item.exerciseId)
                }
            }
        )
    }
}