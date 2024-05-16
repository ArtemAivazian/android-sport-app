package com.aivazart.navigation.view.protein

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
import com.aivazart.navigation.events.ProductEvent
import com.aivazart.navigation.states.ProductState
import com.aivazart.navigation.view.camera.CameraBottomScaffold

@Composable
fun AddProductDialog(
    state: ProductState,
    onEvent: (ProductEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    var showCamera by remember { mutableStateOf(false) }

    if (showCamera) {
        CameraBottomScaffold(onExit = { showCamera = false })
    } else {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                onEvent(ProductEvent.HideDialog)
            },
            title = { Text(text = "Add product") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextField(
                        value = state.name,
                        onValueChange = {
                            onEvent(ProductEvent.SetProductName(it))
                        },
                        placeholder = {
                            Text(text = "Name")
                        }
                    )
                    TextField(
                        value = state.protein,
                        onValueChange = {
                            onEvent(ProductEvent.SetProductProtein(it))
                        },
                        placeholder = {
                            Text(text = "Protein")
                        }
                    )
                    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
                    val photoPickerLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.PickVisualMedia(),
                        onResult = { uri ->
                            selectedImageUri = uri
                            onEvent(ProductEvent.SetProductImageUri(uri))
                        }
                    )
                    IconButton(onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.Upload,
                            contentDescription = "Upload image"
                        )
                    }
                    IconButton(onClick = {
                        showCamera = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = "Take Photo"
                        )
                    }

                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(onClick = {
                        onEvent(ProductEvent.SaveProduct)
                    }) {
                        Text(text = "Save")
                    }
                }
            }
        )
    }
}