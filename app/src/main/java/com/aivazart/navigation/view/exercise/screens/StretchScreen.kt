package com.aivazart.navigation.view.exercise.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.aivazart.navigation.viewmodel.ExerciseViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StretchScreen(viewModel: ExerciseViewModel, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Stretch Screen") })
        }
    ) {
        Text(text = "dgrsdbdzf")
    }

}