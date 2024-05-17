package com.aivazart.navigation.view.workout


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aivazart.navigation.model.ProductEvent
import com.aivazart.navigation.model.ProductState
import com.aivazart.navigation.model.WorkoutEvent
import com.aivazart.navigation.model.WorkoutState

@Composable
fun AddWorkoutDialog(
    state: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit,
    modifier: Modifier = Modifier
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
                    }) {
                        Text(text = "Add")
                    }
                }
            }
        )

}