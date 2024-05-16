package com.aivazart.navigation.states

import android.net.Uri
import com.aivazart.navigation.model.Product

data class ProductState(
    val products: List<Product> = emptyList(),
    val name: String = "",
    val protein: String = "",
    val imageUri: Uri? = null,
    val isAddingProduct: Boolean = false
)