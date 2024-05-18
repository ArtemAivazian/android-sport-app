package com.aivazart.navigation.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aivazart.navigation.R
import com.aivazart.navigation.dao.ExerciseDao
import com.aivazart.navigation.model.Exercise
import com.aivazart.navigation.view.exercise.EXERCISES
import com.aivazart.navigation.view.exercise.screens.RequestState
import com.aivazart.navigation.view.protein.ComposeFileProvider
import com.aivazart.navigation.view.protein.ComposeFileProvider.Companion.getImageUri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ExerciseViewModel(private val exerciseDao: ExerciseDao, private val context: Context) : ViewModel() {

    private val _cardioExercises = MutableStateFlow<RequestState<List<Exercise>>>(RequestState.Idle)
    val cardioExercises: StateFlow<RequestState<List<Exercise>>> = _cardioExercises

    private val _strengthExercises = MutableStateFlow<RequestState<List<Exercise>>>(RequestState.Idle)
    val strengthExercises: StateFlow<RequestState<List<Exercise>>> = _strengthExercises

    private val _stretchExercises = MutableStateFlow<RequestState<List<Exercise>>>(RequestState.Idle)
    val stretchExercises: StateFlow<RequestState<List<Exercise>>> = _stretchExercises

    private val _exercises = MutableStateFlow<RequestState<List<Exercise>>>(RequestState.Idle)
    val exercises: StateFlow<RequestState<List<Exercise>>> = _exercises

    private val _exercisesForWorkout = MutableStateFlow<RequestState<List<Exercise>>>(RequestState.Idle)
    val exercisesForWorkout: StateFlow<RequestState<List<Exercise>>> = _exercisesForWorkout

    init {
        viewModelScope.launch {
            initializeDefaultValues()
        }
    }


    private fun initializeExercises(context: Context): List<Exercise> {
        val runningBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.kyle)
        val cyclingBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.kyle)

        val runningFile = ComposeFileProvider.saveImageToDirectory(context, runningBitmap, "running_image")
        val cyclingFile = ComposeFileProvider.saveImageToDirectory(context, cyclingBitmap, "cycling_image")

        val runningUri = runningFile?.let { ComposeFileProvider.getImageUri(context, "running_image").toString() }
        val cyclingUri = cyclingFile?.let { ComposeFileProvider.getImageUri(context, "cycling_image").toString() }

        return listOf(
            Exercise(name = "Running", description = "Cardio exercise", type = EXERCISES.CARDIO, imageUri = runningUri),
            Exercise(name = "Cycling", description = "Cardio exercise", type = EXERCISES.CARDIO, imageUri = cyclingUri),
            Exercise(name = "Push Up", description = "Strength exercise", type = EXERCISES.STRENGTH),
            Exercise(name = "Pull Up", description = "Strength exercise", type = EXERCISES.STRENGTH),
            Exercise(name = "Yoga", description = "Stretch exercise", type = EXERCISES.STRETCH),
            Exercise(name = "Stretching", description = "Stretch exercise", type = EXERCISES.STRETCH)
        )
    }

    private suspend fun initializeDefaultValues() {
        val existingExercises = exerciseDao.getAllExercises().firstOrNull()
        if (existingExercises.isNullOrEmpty()) {
            val defaultExercises = initializeExercises(context)
            defaultExercises.forEach { exercise ->
                exerciseDao.upsertExercise(exercise)
            }
        }
    }
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

    fun getExercisesForWorkout(ids: List<Int>) {
        _exercisesForWorkout.value = RequestState.Loading
        try {
            viewModelScope.launch {
                exerciseDao.getExercisesByIds(ids).collect{
                    _exercisesForWorkout.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _exercisesForWorkout.value = RequestState.Error(e)
        }
    }


    fun getStretchExercises() {
        _stretchExercises.value = RequestState.Loading
        try {
            viewModelScope.launch {
                exerciseDao.getExercisesByType(EXERCISES.STRETCH).collect {
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

    suspend fun getExercisesByIds(ids: List<Int>): List<Exercise> {
        val exercises = mutableListOf<Exercise>()
        for (id in ids) {
            val exercise = exerciseDao.getExerciseById(id)
            exercises.add(exercise)
        }
        return exercises
    }



}