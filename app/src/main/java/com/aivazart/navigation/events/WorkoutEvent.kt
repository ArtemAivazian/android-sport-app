package com.aivazart.navigation.events

import com.aivazart.navigation.model.Product


sealed interface WorkoutEvent {
    object SaveWorkout: WorkoutEvent
    data class SetWorkoutName(val name: String): WorkoutEvent
    object ShowDialog: WorkoutEvent
    object HideDialog: WorkoutEvent
    data class DeleteWorkout(val product: Product): WorkoutEvent
}