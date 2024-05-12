package com.aivazart.navigation.view.protein

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aivazart.navigation.notification.AndroidAlarmScheduler
import com.aivazart.navigation.viewmodel.BodyStatsViewModel
import com.aivazart.navigation.viewmodel.ProductViewModel
import java.util.Calendar


@Composable
fun ProgressBarScreen(
    productViewModel: ProductViewModel = viewModel(),
    bodyStatsViewModel: BodyStatsViewModel = viewModel()
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "AddProductScreen")
    }

    //I display here circular progress bar that will calculate sum up protein in added products to
    //protein norm. This progress bar must update dynamically, depends on deleting, adding products and also on
    //protein norm in BodyStats


    // Collecting state from view models
    val productState by productViewModel.state.collectAsState()
    val bodyStatsState by bodyStatsViewModel.state.collectAsState()

    // Calculation logic
    val currentProteinTotal = productState.products.sumOf { it.protein.toDoubleOrNull() ?: 0.0 }
    val proteinNorm = bodyStatsState.values["proteinNorm"]?.toDoubleOrNull() ?: 100.0 // Defaulting to a norm of 100 if not available

    val context = LocalContext.current

    // UI Components
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "AddProductScreen")
        ProteinProgressBar(currentProteinTotal, proteinNorm)

        // Time picker dialog to schedule the reminder
        val timePickerDialog = remember {
            TimePickerDialog(context, { _, hourOfDay, minute ->
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                }
                AndroidAlarmScheduler.scheduleProteinReminder(context, calendar.timeInMillis)
            }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), false)
        }

        Button(onClick = {
            timePickerDialog.show()
        }) {
            Text("Set Protein Reminder")
        }

    }
}
@Composable
fun ProteinProgressBar(currentProtein: Double, proteinNorm: Double) {
    val progress = (currentProtein / proteinNorm).coerceIn(0.0, 1.0)  // Ensure progress stays between 0 and 1

    CircularProgressIndicator(
        progress = { progress.toFloat() },
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = ProgressIndicatorDefaults.CircularStrokeWidth,
    )

    // Optional: Add a text label to show numerical value
    Text(text = "${(100 * progress).toInt()}% of $proteinNorm g")
}


