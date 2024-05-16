package com.aivazart.navigation.model

import com.aivazart.navigation.model.Workout
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
@Dao
interface WorkoutDao {

    @Upsert
    suspend fun insert(workout: Workout)

    @Update
    suspend fun update(workout: Workout)

    @Query("SELECT * FROM Workout")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Query("SELECT * FROM Workout WHERE workoutId = :id")
    suspend fun getWorkoutById(id: Int): Workout

    @Transaction
    suspend fun addIdToList(workoutId: Int, newId: Int) {
        val workout = getWorkoutById(workoutId)

        val updatedList = workout.listOfExercisesIds.toMutableList().apply {
            add(newId)
        }

        update(workout.copy(listOfExercisesIds = updatedList))
    }
}
