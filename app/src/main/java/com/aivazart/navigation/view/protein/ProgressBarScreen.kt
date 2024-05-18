package com.aivazart.navigation.view.protein

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aivazart.navigation.R
import com.aivazart.navigation.notification.AndroidAlarmScheduler
import com.aivazart.navigation.viewmodel.BodyStatsViewModel
import com.aivazart.navigation.viewmodel.ProductViewModel
import java.util.Calendar


@Composable
fun ProgressBarScreen(
    productViewModel: ProductViewModel = viewModel(),
    bodyStatsViewModel: BodyStatsViewModel = viewModel()
) {
    // Collecting state from view models
    val productState by productViewModel.state.collectAsState()
    val bodyStatsState by bodyStatsViewModel.state.collectAsState()

    // Calculation logic
    val currentProteinTotal = productState.products.sumOf { it.protein.toDoubleOrNull() ?: 0.0 }
    val proteinNorm = bodyStatsState.values["proteinNorm"]?.toDoubleOrNull() ?: 100.0 // Defaulting to a norm of 100 if not available
    val context = LocalContext.current

    // UI Components
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()  // Fills the entire screen
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            ProteinProgressBar(currentProteinTotal, proteinNorm)
            Spacer(modifier = Modifier.height(16.dp))

            // Button to show the TimePickerDialog
            val timePickerDialog = remember {
                TimePickerDialog(context, { _, hourOfDay, minute ->
                    val calendar = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, hourOfDay)
                        set(Calendar.MINUTE, minute)
                        set(Calendar.SECOND, 0)
                    }
                    val timeInMillis = calendar.timeInMillis
                    AndroidAlarmScheduler.scheduleProteinReminder(context, timeInMillis)

                    // Save the scheduled time to SharedPreferences
                    val sharedPrefs = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
                    with(sharedPrefs.edit()) {
                        putLong("scheduledTime", timeInMillis)
                        apply()
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), false)
            }
            Button(
                onClick = { timePickerDialog.show() },
                modifier = Modifier
                        .padding(16.dp)
                    .widthIn(max = 250.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                ),
                border = BorderStroke(1.dp, Color.White)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.notify), // Use appropriate icon
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Notify me to take protein",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Composable
fun ProteinProgressBar(currentProtein: Double, proteinNorm: Double) {
    val progress = (currentProtein / proteinNorm).coerceIn(0.0, 1.0)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
    ) {
        CircularProgressIndicator(
            progress = { progress.toFloat() },
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF76FF03),  // Bright green progress color
            strokeWidth = 8.dp,
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${(100 * progress).toInt()} %",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "You can do it!",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            )
        }
    }
}


