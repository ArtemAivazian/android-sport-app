package com.aivazart.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aivazart.navigation.model.Exercise
import com.aivazart.navigation.model.ExerciseDao
import com.aivazart.navigation.model.Product
import com.aivazart.navigation.model.ProductEvent
import com.aivazart.navigation.model.ProductState
import com.aivazart.navigation.model.Workout
import com.aivazart.navigation.model.WorkoutDao
import com.aivazart.navigation.model.WorkoutEvent
import com.aivazart.navigation.model.WorkoutState
import com.aivazart.navigation.view.exercise.EXERCISES
import com.aivazart.navigation.view.exercise.screens.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

import kotlinx.coroutines.launch

class WorkoutViewModel(private val workoutDao: WorkoutDao) : ViewModel() {
//
//    private val _workouts = MutableStateFlow<RequestState<List<Workout>>>(RequestState.Idle)
//    val workouts: StateFlow<RequestState<List<Workout>>> = _workouts

    private val _workouts = workoutDao.getAllWorkouts()


    private val _state = MutableStateFlow(WorkoutState())
    //public state for exposing Immutable State to UI
    val state = combine(_state, _workouts) { state, workouts ->
        state.copy(
            workouts = workouts
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WorkoutState())

    fun onEvent(event: WorkoutEvent) {
        when(event) {
            is WorkoutEvent.DeleteWorkout -> {
                //to execute in a coroutine is needed to execute in viewModelScope.launch {..}
                viewModelScope.launch {
//                    workoutDao.deleteProduct(event.product)
                }
            }
            WorkoutEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingWorkout = false
                ) }
            }
            WorkoutEvent.SaveWorkout -> {
                val name = state.value.name

                if(name.isBlank()){
                    return
                }

                val workout = Workout(
                    name = name,
                    listOfExercisesIds = listOf()
                )

                viewModelScope.launch {
                    workoutDao.insert(workout)
                }
                _state.update { it.copy(
                    isAddingWorkout = false,
                    name = "",
                )}
            }
            is WorkoutEvent.SetWorkoutName -> {
                _state.update { it.copy(
                    name = event.name
                ) }
            }

            WorkoutEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingWorkout = true
                ) }
            }
        }

    }


//    init {
//        getCardioExercises()
//    }

//    fun getWorkouts() {
//        _workouts.value = RequestState.Loading
//        try {
//            viewModelScope.launch {
//                workoutDao.getAllWorkouts().collect {
//                    _workouts.value = RequestState.Success(it)
//                }
//            }
//        } catch (e: Exception) {
//            _workouts.value = RequestState.Error(e)
//        }
//    }
    suspend fun getWorkout(id:Int): Workout {
        return workoutDao.getWorkoutById(id)
    }

}