package com.aivazart.navigation.view.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aivazart.navigation.events.BodyStatsEvent
import com.aivazart.navigation.states.BodyStatsState

val measurementUnits = mapOf(
    "proteinNorm" to "g", // grams
    "height" to "cm",     // centimeters
    "weight" to "kg",     // kilograms
    "bodyFat" to "%",     // percentage
    "chest" to "cm",      // centimeters
    "waist" to "cm",      // centimeters
    "hips" to "cm",       // centimeters
    "biceps" to "cm"      // centimeters
)
@Composable
fun BodyStatsScreen(
    state: BodyStatsState,
    onEvent: (BodyStatsEvent) -> Unit
) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Settings",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(thickness = 1.dp, color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Body statistics",
                    color = Color(0xFF00FF00), // Green color
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .background(Color.Black),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.values.entries.toList()) { (key, value) ->
                    PropertyRow(state = state, key = key, value = value, onEvent = onEvent)
                }
            }
        }
    )
}

@Composable
private fun PropertyRow(
    state: BodyStatsState,
    key: String,
    value: String,
    onEvent: (BodyStatsEvent) -> Unit
) {
    val unit = measurementUnits[key] ?: ""
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF141218))
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        if (state.dialogVisibility[key] == true) {
            UpdateStatisticsDialog(propertyKey = key, propertyValue = value, onEvent = onEvent)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF141218))
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = key.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                modifier = Modifier.weight(1f)
            )
            TextButton(
                onClick = { onEvent(BodyStatsEvent.ShowDialog(key)) }
            ) {
                Text(
                    text = "$value $unit",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}
