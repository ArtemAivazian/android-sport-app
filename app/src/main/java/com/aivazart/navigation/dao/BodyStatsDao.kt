package com.aivazart.navigation.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aivazart.navigation.model.BodyStatistics
import kotlinx.coroutines.flow.Flow

@Dao
interface BodyStatsDao {
    @Query("SELECT * FROM bodystatistics LIMIT 1")
    fun getBodyStatistics(): Flow<BodyStatistics?>

    @Insert
    suspend fun insertBodyStatistics(stats: BodyStatistics)

    @Update
    suspend fun updateBodyStatistics(stats: BodyStatistics)
}