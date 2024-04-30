package com.aivazart.navigation.model

data class ProductState(
    val products: List<Product> = emptyList(),
    val name: String = "",
    val protein: String = "",
    val isAddingProduct: Boolean = false
)