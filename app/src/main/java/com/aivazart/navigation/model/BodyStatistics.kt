package com.aivazart.navigation.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BodyStatistics(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val proteinNorm: String, //in grams
    val height: String, // in cm
    val weight: String, // in kg
    val bodyFat: String, // in percentage
    val chest: String, // in cm
    val waist: String, // in cm
    val hips: String, // in cm
    val biceps: String // in cm
)
