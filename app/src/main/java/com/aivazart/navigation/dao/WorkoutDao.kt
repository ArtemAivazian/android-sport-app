package com.aivazart.navigation.dao

import com.aivazart.navigation.model.Workout
import androidx.room.Dao
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

    @Query("SELECT workoutId FROM Workout WHERE name = :name")
    suspend fun getWorkoutIdByName(name: String): Int


    @Transaction
    suspend fun addIdsToList(workoutId: Int, newIds: List<Int>) {
        val workout = getWorkoutById(workoutId)

        val updatedList = workout.listOfExercisesIds.toMutableList().apply {
            addAll(newIds)
        }

        update(workout.copy(listOfExercisesIds = updatedList))
    }
}
