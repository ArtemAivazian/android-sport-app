package com.aivazart.navigation.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.aivazart.navigation.view.exercise.EXERCISES
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Upsert
    suspend fun upsertExercise(exercise: Exercise)

    @Delete
    suspend fun deletePExercise(exercise: Exercise)

    @Query("SELECT * FROM exercise")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM Exercise WHERE type = :exerciseType")
    fun getExercisesByType(exerciseType: EXERCISES): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE exerciseId = :id")
    suspend fun getExerciseById(id: Int): Exercise
}