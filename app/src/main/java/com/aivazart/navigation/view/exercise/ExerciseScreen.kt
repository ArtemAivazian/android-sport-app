package com.aivazart.navigation.view.exercise

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aivazart.navigation.viewmodel.ExerciseViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(exerciseViewModel: ExerciseViewModel, navController: NavHostController) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Exercise Screen") })
        }
    ) {
        ExerciseList(navController)
    }
}

@Composable
fun ExerciseList(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        EXERCISES.values().forEach { item ->
            ExerciseListItem(item) {
                when (item) {
                    EXERCISES.STRENGTH -> navController.navigate("Strength")
                    EXERCISES.CARDIO -> navController.navigate("Cardio")
                    EXERCISES.STRETCH -> navController.navigate("Stretch")
                }
            }
        }
    }
}

@Composable
fun ExerciseListItem(item: EXERCISES, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onItemClick)
    ) {
        Text(text = item.name)
    }
}


