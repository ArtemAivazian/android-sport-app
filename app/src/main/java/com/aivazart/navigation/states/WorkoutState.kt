package com.aivazart.navigation.states

import com.aivazart.navigation.model.Workout

data class WorkoutState(
    val workouts: List<Workout> = emptyList(),
    val name: String = "",
    val isAddingWorkout: Boolean = false
)
