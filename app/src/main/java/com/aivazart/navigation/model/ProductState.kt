package com.aivazart.navigation.model

import android.net.Uri

data class ProductState(
    val products: List<Product> = emptyList(),
    val name: String = "",
    val protein: String = "",
    val imageUri: Uri? = null,
    val isAddingProduct: Boolean = false
)