package com.aivazart.navigation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aivazart.navigation.model.BodyStatistics
import com.aivazart.navigation.model.BodyStatsDao
import com.aivazart.navigation.model.BodyStatsEvent
import com.aivazart.navigation.model.BodyStatsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BodyStatsViewModel(private val dao: BodyStatsDao) : ViewModel() {

    init {
        viewModelScope.launch {
            initializeDefaultValues()
        }
    }

    private suspend fun initializeDefaultValues() {
        val exists = dao.getBodyStatistics().firstOrNull() == null
        if (exists) {
            Log.d("BodyStatsViewModel", "Initializing default values in database.")
            val defaultStats = BodyStatistics(
                proteinNorm = "-",
                height = "-",
                weight = "-",
                bodyFat = "-",
                chest =  "-",
                waist = "-",
                hips = "-",
                biceps = "-"
            ) // Default value
            dao.insertBodyStatistics(defaultStats)
        }
    }

    private val _bodyStats = dao.getBodyStatistics()

    private val _state = MutableStateFlow(BodyStatsState())
    val state = combine(_state, _bodyStats) { state, bodyStats ->
        bodyStats?.let {
            state.copy(
                values = mapOf(
                    "proteinNorm" to it.proteinNorm,
                    "height" to it.height,
                    "weight" to it.weight,
                    "bodyFat" to it.bodyFat,
                    "chest" to it.chest,
                    "waist" to it.waist,
                    "hips" to it.hips,
                    "biceps" to it.biceps
                ),
                dialogVisibility = state.dialogVisibility
            )
        } ?: state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BodyStatsState())

    fun onEvent(event: BodyStatsEvent) {
        Log.d("BodyStatsViewModel", "Event received: $event")
        when (event) {
            is BodyStatsEvent.UpdateStatistic -> {
                _state.update { currentState ->
                    currentState.copy(
                        values = currentState.values.toMutableMap().apply { put(event.key, event.value) }
                    )
                }
                viewModelScope.launch {
                    _bodyStats.firstOrNull()?.let { bodyStats ->
                        val updatedStats = bodyStats.copyByEvent(event.key, event.value)
                        dao.updateBodyStatistics(updatedStats)
                    }
                    _state.update { it.copy(dialogVisibility = it.dialogVisibility.toMutableMap().apply { put(event.key, false) }) }
                }
            }
            is BodyStatsEvent.ShowDialog -> _state.update { it.copy(dialogVisibility = it.dialogVisibility.toMutableMap().apply { put(event.key, true) }) }
            is BodyStatsEvent.HideDialog -> _state.update { it.copy(dialogVisibility = it.dialogVisibility.toMutableMap().apply { put(event.key, false) }) }
        }
    }
}

private fun BodyStatistics.copyByEvent(key: String, value: String): BodyStatistics {
    return when (key) {
        "proteinNorm" -> copy(proteinNorm = value)
        "height" -> copy(height = value)
        "weight" -> copy(weight = value)
        "bodyFat" -> copy(bodyFat = value)
        "chest" -> copy(chest = value)
        "waist" -> copy(waist = value)
        "hips" -> copy(hips = value)
        "biceps" -> copy(biceps = value)
        else -> this
    }
}
