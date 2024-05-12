package com.aivazart.navigation.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Product::class,Exercise::class,BodyStatistics::class],
    version = 8
)
abstract class ProductDatabase: RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val exerciseDao: ExerciseDao
    abstract val bodyStatsDao: BodyStatsDao

}