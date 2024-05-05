package com.aivazart.navigation.view.exercise.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aivazart.navigation.R
import com.aivazart.navigation.model.Exercise
import com.aivazart.navigation.viewmodel.ExerciseViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailsScreen(exercise: Int, viewModel: ExerciseViewModel,  navController: NavHostController) {

    var loadedExercise by remember { mutableStateOf<Exercise?>(null) }
    LaunchedEffect(Unit) {
        loadedExercise = viewModel.getExercise(exercise)
    }

    var selectedIndex by remember { mutableStateOf(0) }
    val options = listOf("Description", "Image")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    loadedExercise?.name?.let {
                        Text(it, overflow = TextOverflow.Ellipsis) }
            },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },)
        },
    ){

        Column(modifier = Modifier.fillMaxWidth()) {

        //segmented button
            Spacer(modifier = Modifier.height(60.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            onClick = { selectedIndex = index },
                            selected = index == selectedIndex
                        ) {
                            Text(label)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

        //data
        Row(modifier = Modifier.fillMaxWidth()) {
            loadedExercise?.let { exercise ->
                when (selectedIndex) {
                    0 -> {
                        Text(text = exercise.description)
                    }

                    1 -> {
                        //TODO
//                            exercise.imageUri?.let { uri ->
//                            Image(
//                                painter = painterResource(), // Provide your image resource here
//                                contentDescription = "Exercise Image"
//                            )
//                        }
                    }
                }
            }
        }
    }

    }
}


