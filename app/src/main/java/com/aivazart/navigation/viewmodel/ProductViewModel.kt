package com.aivazart.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aivazart.navigation.model.Product
import com.aivazart.navigation.dao.ProductDao
import com.aivazart.navigation.events.ProductEvent
import com.aivazart.navigation.states.ProductState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(private val dao: ProductDao): ViewModel() {

    private val _products = dao.getAllProducts()

    private val _state = MutableStateFlow(ProductState())
    //public state for exposing Immutable State to UI
    val state = combine(_state, _products) { state, products ->
        state.copy(
            products = products
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())



    fun onEvent(event: ProductEvent) {
        when(event) {
            is ProductEvent.DeleteProduct -> {
                //to execute in a coroutine is needed to execute in viewModelScope.launch {..}
                viewModelScope.launch {
                    dao.deleteProduct(event.product)
                }
            }
            ProductEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingProduct = false
                ) }
            }
            ProductEvent.SaveProduct -> {
                val name = state.value.name
                val protein = state.value.protein
                val imageUri = state.value.imageUri?.toString()

                if(name.isBlank() || protein.isBlank() || imageUri.isNullOrBlank()){
                    return
                }

                val product = Product(
                    name = name,
                    protein = protein,
                    imageUri = imageUri
                )

                viewModelScope.launch {
                    dao.upsertProduct(product)
                }
                _state.update { it.copy(
                    isAddingProduct = false,
                    name = "",
                    protein = "",
                    imageUri = null
                )}
            }
            is ProductEvent.SetProductName -> {
                _state.update { it.copy(
                    name = event.name
                ) }
            }
            is ProductEvent.SetProductProtein -> {
                _state.update { it.copy(
                    protein = event.protein
                ) }
            }
            ProductEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingProduct = true
                ) }
            }

            is ProductEvent.SetProductImageUri -> {
                _state.update {it.copy(
                    imageUri = event.imageUri
                ) }
            }
        }

    }

}