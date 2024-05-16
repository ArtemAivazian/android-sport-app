package com.aivazart.navigation.model

import androidx.room.TypeConverter

class WorkoutConverter {
    @TypeConverter
    fun fromList(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String): List<Int> {
        if (value.isEmpty()) {
            return emptyList() // Return an empty list if the input string is empty
        }
        return value.split(",").map { it.toInt() }
    }
}
