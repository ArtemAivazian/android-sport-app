package com.aivazart.navigation.view.exercise.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.aivazart.navigation.viewmodel.ExerciseViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StrengthScreen(viewModel: ExerciseViewModel, navController: NavHostController) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Exercise Screen") })
            }
        ) {
            Text(text = "dgrsdbdzf")
        }

}