package com.aivazart.navigation.view.exercise.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.aivazart.navigation.model.Exercise
import com.aivazart.navigation.view.exercise.EXERCISES
import com.aivazart.navigation.viewmodel.ExerciseViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardioScreen(viewModel: ExerciseViewModel, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Cardio Screen") })
        }
    ) {

        LaunchedEffect(Unit) {
            viewModel.getExercisesByType(EXERCISES.CARDIO)
        }
        val exercises by viewModel.exercisesByType.collectAsState(emptyList())

        LazyColumn {
            items(exercises) { item ->
                CardioListItem(item) {
                    navController.navigate("ExerciseDetails/${item.exerciseId}")
                }
            }
        }
    }
}

@Composable
fun CardioListItem(item: Exercise, onItemClick: () -> Unit) {
    Column {
        Row {
            Text(text = item.name)
        }
    }
}