package com.aivazart.navigation.view.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aivazart.navigation.events.BodyStatsEvent
import com.aivazart.navigation.states.BodyStatsState

@Composable
fun BodyStatsScreen(
    state: BodyStatsState,
    onEvent: (BodyStatsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Body Statistics",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        state.values.forEach { (key, value) ->
            PropertyRow(state, key, value, onEvent)
        }
    }
}

@Composable
private fun PropertyRow(
    state: BodyStatsState,
    key: String,
    value: String,
    onEvent: (BodyStatsEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (state.dialogVisibility[key] == true) {
            UpdateStatisticsDialog(propertyKey = key, propertyValue = value, onEvent = onEvent)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = key.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            TextButton(
                onClick = { onEvent(BodyStatsEvent.ShowDialog(key)) },
                modifier = Modifier
            ) {
                Text(
                    text = value,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
