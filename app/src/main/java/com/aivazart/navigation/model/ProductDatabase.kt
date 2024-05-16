package com.aivazart.navigation.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aivazart.navigation.dao.BodyStatsDao
import com.aivazart.navigation.dao.ExerciseDao
import com.aivazart.navigation.dao.ProductDao
import com.aivazart.navigation.dao.WorkoutDao

@Database(
    entities = [Product::class,Exercise::class, BodyStatistics::class, Workout::class],
    version = 10
)
@TypeConverters(WorkoutConverter::class)
abstract class ProductDatabase: RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val exerciseDao: ExerciseDao
    abstract val workoutDao: WorkoutDao
    abstract val bodyStatsDao: BodyStatsDao

}