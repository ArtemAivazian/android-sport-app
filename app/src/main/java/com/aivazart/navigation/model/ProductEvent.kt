package com.aivazart.navigation.model

import android.net.Uri

sealed interface ProductEvent {
    object SaveProduct: ProductEvent
    data class SetProductName(val name: String): ProductEvent
    data class SetProductProtein(val protein: String): ProductEvent
    data class SetProductImageUri(val imageUri: Uri?) : ProductEvent
    object ShowDialog: ProductEvent
    object HideDialog: ProductEvent
    data class DeleteProduct(val product: Product): ProductEvent

}