package com.aivazart.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aivazart.navigation.model.Exercise
import com.aivazart.navigation.model.ExerciseDao
import com.aivazart.navigation.view.exercise.EXERCISES
import com.aivazart.navigation.view.exercise.screens.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

import kotlinx.coroutines.launch

class ExerciseViewModel(private val exerciseDao: ExerciseDao) : ViewModel() {


//    private val _allExercises = MutableStateFlow<List<Exercise>>(emptyList())
//    val allExercises: StateFlow<List<Exercise>> = _allExercises
//
//    private val _exercisesByType = MutableStateFlow<List<Exercise>>(emptyList())
//    val exercisesByType: StateFlow<List<Exercise>> = _exercisesByType
//
//    fun getAllExercises() {
//        viewModelScope.launch {
//            exerciseDao.getAllExercises().collect { exercises ->
//                _allExercises.value = exercises
//            }
//        }
//    }
//
//    fun getExercisesByType(type:EXERCISES) {
//        viewModelScope.launch {
//            exerciseDao.getExercisesByType(type).collect { exercises ->
//                _exercisesByType.value = exercises
//            }
//        }
//    }


    private val _cardioExercises = MutableStateFlow<RequestState<List<Exercise>>>(RequestState.Idle)
    val cardioExercises: StateFlow<RequestState<List<Exercise>>> = _cardioExercises
    init {
        getCardioExercises()
    }

//    fun getCardioExercises() {
//        viewModelScope.launch {
//            _cardioExercises.value = RequestState.Loading
//            try {
//                val exercises = exerciseDao.getExercisesByType(EXERCISES.CARDIO).first()
//                _cardioExercises.value = RequestState.Success(exercises)
//            } catch (e: Exception) {
//                _cardioExercises.value = RequestState.Error(e)
//            }
//        }
//    }

    private fun getCardioExercises() {
        _cardioExercises.value = RequestState.Loading
        try {
            viewModelScope.launch {
                exerciseDao.getExercisesByType(EXERCISES.CARDIO).collect {
                    _cardioExercises.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _cardioExercises.value = RequestState.Error(e)
        }
    }


}