package com.example.icare.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.icare.data.RepoProvider
import com.example.icare.data.db.BeautyTask
import com.example.icare.data.db.BeautyLog
import com.example.icare.data.db.ExerciseLog
import com.example.icare.domain.Calculations
import com.example.icare.util.DateUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = RepoProvider.get(application)

    // Beauty tasks
    val skin: StateFlow<List<BeautyTask>> = repo.tasks("Skin")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val hair: StateFlow<List<BeautyTask>> = repo.tasks("Hair")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val body: StateFlow<List<BeautyTask>> = repo.tasks("Body")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val nails: StateFlow<List<BeautyTask>> = repo.tasks("Nails")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val todayLogs: StateFlow<List<BeautyLog>> = repo.logsToday()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // Settings
    val weightKg: StateFlow<Int> = repo.settings()
        .map { it?.weightKg ?: 57 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 57)

    // Settings → water goal (ml)
    val waterGoal: StateFlow<Int> = repo.settings()
        .map { it?.waterGoalMl ?: 2000 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 2000)

    // Today’s water total
    val waterToday: StateFlow<Int> = repo.waterToday()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    fun addWater(ml: Int) {
        viewModelScope.launch { repo.addWater(ml) }
    }


    // Exercise logs (last 7 days)
    private val fromMs = DateUtils.startOfDayMillis(daysAgo = 6)
    private val toMs = System.currentTimeMillis()
    val weekExercises: StateFlow<List<ExerciseLog>> = repo.exerciseRange(fromMs, toMs)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun setTaskDone(taskId: Long, done: Boolean) {
        viewModelScope.launch { repo.setTaskDoneToday(taskId, done) }
    }

    fun addSurya(rounds: Int, minutes: Int) {
        viewModelScope.launch {
            val kcal = Calculations.suryaCalories(rounds, weightKg.value)
            repo.addExercise("Surya Namaskar", rounds, minutes, kcal)
        }
    }
}
