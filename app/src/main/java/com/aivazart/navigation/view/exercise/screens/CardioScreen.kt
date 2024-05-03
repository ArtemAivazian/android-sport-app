package com.aivazart.navigation.view.exercise.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.aivazart.navigation.viewmodel.ExerciseViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardioScreen(viewModel: ExerciseViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Cardio Screen") })
        }
    ) {
        Text(text = "dgrsdbdzf")
    }

}