package com.aivazart.navigation.model

import android.net.Uri

data class WorkoutState(
    val workouts: List<Workout> = emptyList(),
    val name: String = "",
    val isAddingWorkout: Boolean = false
)
