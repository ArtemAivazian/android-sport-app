package com.aivazart.navigation.view.exercise.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.aivazart.navigation.model.Exercise
import com.aivazart.navigation.viewmodel.ExerciseViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StrengthScreen(viewModel: ExerciseViewModel, navController: NavHostController) {

    LaunchedEffect(Unit) {
        viewModel.getStrengthExercises()
    }
    val strengthExercises by viewModel.strengthExercises.collectAsState()

    Scaffold(
        topBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(12.dp)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                    Spacer(modifier = Modifier.width(93.dp))

                    Text(
                        text = "Strength",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterVertically).weight(0.5f)
                    )

                }
                HorizontalDivider(thickness = 1.dp, color = Color.White)
            }
        }
    ){
        StrengthExercisesList(
            modifier = Modifier.background(Color.Black),
            strengthExercises = strengthExercises,
            navController
        )
    }

}

@Composable
fun StrengthExercisesList( modifier: Modifier = Modifier,
                         strengthExercises: RequestState<List<Exercise>>,
                         navController: NavHostController) {

    var loadedStrengthExercises: List<Exercise> = emptyList()

    if (strengthExercises is RequestState.Success) {
        loadedStrengthExercises = strengthExercises.data
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        item { Spacer(modifier = Modifier.height(76.dp)) }
        items(loadedStrengthExercises) { item ->
            StrengthListItem(item) {
                navController.navigate("ExerciseDetails/${item.exerciseId}")
            }
        }
    }
}
@Composable
fun StrengthListItem(item: Exercise, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ){
        Row (
            Modifier
                .clickable(onClick = onItemClick)
                .background(Color(0xFF141218))
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUri,
                contentDescription = "Exercise Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = item.name,
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                modifier = Modifier.weight(1f)
            )
        }
    }
}