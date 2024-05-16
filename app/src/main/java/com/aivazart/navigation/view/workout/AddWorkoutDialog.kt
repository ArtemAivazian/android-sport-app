package com.aivazart.navigation.view.workout


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aivazart.navigation.events.WorkoutEvent
import com.aivazart.navigation.states.WorkoutState

@Composable
fun AddWorkoutDialog(
    state: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                onEvent(WorkoutEvent.HideDialog)
            },
            title = { Text(text = "Add workout") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextField(
                        value = state.name,
                        onValueChange = {
                            onEvent(WorkoutEvent.SetWorkoutName(it))
                        },
                        placeholder = {
                            Text(text = "Name")
                        }
                    )
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(onClick = {
                        onEvent(WorkoutEvent.SaveWorkout)
                        navController.navigate("ChooseExercisesScreen")
                    }) {
                        Text(text = "Add")
                    }
                }
            }
        )

}