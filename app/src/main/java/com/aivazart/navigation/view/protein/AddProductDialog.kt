package com.aivazart.navigation.view.protein

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aivazart.navigation.events.ProductEvent
import com.aivazart.navigation.states.ProductState
import coil.compose.AsyncImage
import com.aivazart.navigation.R

@Composable
fun AddProductDialog(
    state: ProductState,
    onEvent: (ProductEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    var showCamera by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val newTempUri = rememberSaveable { mutableStateOf<Uri?>(null) }

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

                val cameraLauncherFullImage = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.TakePicture(),
                    onResult = { success ->
                        if (success) {
                            selectedImageUri = newTempUri.value
                            onEvent(ProductEvent.SetProductImageUri(newTempUri.value))
                        }
                    }
                )
                AsyncImage(
                    model = selectedImageUri ?: R.drawable.outline_add_a_photo_24,
                    contentDescription = "Add photo",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(200.dp)
                        .border(BorderStroke(1.dp, Color.Black))
                        .clickable {
                            val file = ComposeFileProvider.getTempImageFile(context)
                            newTempUri.value = ComposeFileProvider.getUriForFile(context, file)
                            cameraLauncherFullImage.launch(newTempUri.value)
                        }
                )
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
                    // Copy the image to the private directory before saving the product
                    selectedImageUri?.let { uri ->
                        ComposeFileProvider.copyUriToPrivateFileDir(
                            context = context,
                            sourceUri = uri,
                            destFileName = "product_image.jpg"
                        )
                    }
                    onEvent(ProductEvent.SaveProduct)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}