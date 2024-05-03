package com.aivazart.navigation.view.protein

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.aivazart.navigation.viewmodel.ProductViewModel

@Composable
fun ProteinScreen(productViewModel: ProductViewModel) {
    Text(text = "Protein Screen")
    ProteinTabs(productViewModel)
}