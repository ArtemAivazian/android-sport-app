package com.aivazart.navigation.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Upsert
    suspend fun upsertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<Product>>
}