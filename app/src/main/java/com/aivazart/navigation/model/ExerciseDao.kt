package com.aivazart.navigation.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Upsert
    suspend fun upsertExercise(exercise: Exercise)

    @Delete
    suspend fun deletePExercise(exercise: Exercise)

    @Query("SELECT * FROM exercise")
    fun getAllExercises(): Flow<List<Exercise>>
}