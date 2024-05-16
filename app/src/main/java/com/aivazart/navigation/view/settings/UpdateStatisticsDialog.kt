package com.aivazart.navigation.view.settings

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.aivazart.navigation.events.BodyStatsEvent

@Composable
fun UpdateStatisticsDialog(
    propertyKey: String,
    propertyValue: String,
    onEvent: (BodyStatsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val (text, setText) = remember { mutableStateOf(propertyValue) }
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(BodyStatsEvent.HideDialog(propertyKey)) },
        title = { Text("Update $propertyKey") },
        text = {
            TextField(
                value = text,
                onValueChange = setText,
                label = { Text("Enter $propertyKey") }
            )
        },
        confirmButton = {
            Button(
                onClick = { onEvent(BodyStatsEvent.UpdateStatistic(propertyKey, text)) }
            ) { Text("Save") }
        }
    )
}