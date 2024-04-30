package com.aivazart.navigation.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.aivazart.navigation.viewmodel.ProductViewModel

@Composable
fun ProteinScreen(productViewModel: ProductViewModel) {
    Text(text = "Protein Screen")
    ProteinTabs(productViewModel)
}