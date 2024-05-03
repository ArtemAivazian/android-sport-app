package com.aivazart.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aivazart.navigation.model.Exercise
import com.aivazart.navigation.model.ExerciseDao
import com.aivazart.navigation.view.exercise.EXERCISES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ExerciseViewModel(private val exerciseDao: ExerciseDao) : ViewModel() {


    private val _allExercises = MutableStateFlow<List<Exercise>>(emptyList())
    val allExercises: Flow<List<Exercise>> = _allExercises

    private val _exercisesByType = MutableStateFlow<List<Exercise>>(emptyList())
    val exercisesByType: Flow<List<Exercise>> = _allExercises

    fun getAllExercises() {
        viewModelScope.launch {
            exerciseDao.getAllExercises().collect { exercises ->
                _allExercises.value = exercises
            }
        }
    }
    fun getExercisesByType(type:EXERCISES) {
        viewModelScope.launch {
            exerciseDao.getExercisesByType(type).collect { exercises ->
                _exercisesByType.value = exercises
            }
        }
    }
}