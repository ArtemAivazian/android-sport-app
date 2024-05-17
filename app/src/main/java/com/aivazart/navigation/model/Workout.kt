package com.aivazart.navigation.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(indices = [Index(value = ["name"], unique = true)])
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val workoutId: Int = 0,
    val name: String,
    @TypeConverters(WorkoutConverter::class)
    val listOfExercisesIds: List<Int>
)




