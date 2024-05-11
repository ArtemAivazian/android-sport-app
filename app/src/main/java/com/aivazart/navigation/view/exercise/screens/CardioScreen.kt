package com.aivazart.navigation.view.exercise.screens

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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.aivazart.navigation.model.Exercise
import com.aivazart.navigation.viewmodel.ExerciseViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardioScreen(viewModel: ExerciseViewModel, navController: NavHostController) {

    LaunchedEffect(Unit) {
        viewModel.getCardioExercises()
    }

    val cardioExercises by viewModel.cardioExercises.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                        Text("Cardio", overflow = TextOverflow.Ellipsis)
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

        content = { padding ->
            CardioExercisesList(
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
                cardioExercises = cardioExercises,
                navController
            )
        }
    )

}

@Composable
fun CardioExercisesList( modifier: Modifier = Modifier,
                         cardioExercises: RequestState<List<Exercise>>,
                         navController: NavHostController) {

    var loadedCardioExercises: List<Exercise> = emptyList()

    if (cardioExercises is RequestState.Success) {
        loadedCardioExercises = cardioExercises.data
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(60.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(loadedCardioExercises) { item ->
            CardioListItem(item) {
                navController.navigate("ExerciseDetails/${item.exerciseId}")
            }
        }
    }

}
@Composable
fun CardioListItem(item: Exercise, onItemClick: () -> Unit) {
        Row (
            Modifier.clickable(onClick = onItemClick).fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            AsyncImage(
                model = item.imageUri,
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(45.dp)
            )
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            Column(
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ){
                Text(
                    text = item.name,
                    fontSize = 20.sp
                )
            }

//            Text(text = item.name)
        }

}