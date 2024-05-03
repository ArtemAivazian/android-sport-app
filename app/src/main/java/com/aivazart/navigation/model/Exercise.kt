package com.aivazart.navigation.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aivazart.navigation.view.exercise.EXERCISES

@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Int = 0,
    val name: String,
    val description: String,
    val type: EXERCISES,
    val imageUri: String? = null
)