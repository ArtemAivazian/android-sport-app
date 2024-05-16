package com.aivazart.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aivazart.navigation.model.Exercise
import com.aivazart.navigation.dao.ExerciseDao
import com.aivazart.navigation.view.exercise.EXERCISES
import com.aivazart.navigation.view.exercise.screens.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch

class ExerciseViewModel(private val exerciseDao: ExerciseDao) : ViewModel() {

    private val _cardioExercises = MutableStateFlow<RequestState<List<Exercise>>>(RequestState.Idle)
    val cardioExercises: StateFlow<RequestState<List<Exercise>>> = _cardioExercises

    private val _strengthExercises = MutableStateFlow<RequestState<List<Exercise>>>(RequestState.Idle)
    val strengthExercises: StateFlow<RequestState<List<Exercise>>> = _strengthExercises

    private val _stretchExercises = MutableStateFlow<RequestState<List<Exercise>>>(RequestState.Idle)
    val stretchExercises: StateFlow<RequestState<List<Exercise>>> = _stretchExercises

    private val _exercises = MutableStateFlow<RequestState<List<Exercise>>>(RequestState.Idle)
    val exercises: StateFlow<RequestState<List<Exercise>>> = _exercises

//    init {
//        getCardioExercises()
//    }
    fun getExercises() {
        _exercises.value = RequestState.Loading
        try {
            viewModelScope.launch {
                exerciseDao.getAllExercises().collect {
                    _exercises.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _exercises.value = RequestState.Error(e)
        }
    }

     fun getCardioExercises() {
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

    fun getStrengthExercises() {
        _strengthExercises.value = RequestState.Loading
        try {
            viewModelScope.launch {
                exerciseDao.getExercisesByType(EXERCISES.STRENGTH).collect {
                    _strengthExercises.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _strengthExercises.value = RequestState.Error(e)
        }
    }

    fun getStretchExercises() {
        _stretchExercises.value = RequestState.Loading
        try {
            viewModelScope.launch {
                exerciseDao.getExercisesByType(EXERCISES.CARDIO).collect {
                    _stretchExercises.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _stretchExercises.value = RequestState.Error(e)
        }
    }

    suspend fun getExercise(id:Int): Exercise {
        return exerciseDao.getExerciseById(id)
    }



}