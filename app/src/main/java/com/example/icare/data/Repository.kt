package com.example.icare.data

import android.content.Context
import com.example.icare.data.db.*
import com.example.icare.util.DateUtils
import kotlinx.coroutines.flow.Flow

class Repository(private val db: AppDatabase) {

    // Beauty
    fun tasks(category: String): Flow<List<BeautyTask>> =
        db.beautyTaskDao().tasksByCategory(category)

    fun logsToday(): Flow<List<BeautyLog>> =
        db.beautyLogDao().logsForDate(DateUtils.today())

    suspend fun setTaskDoneToday(taskId: Long, done: Boolean) {
        val date = DateUtils.today()
        db.beautyLogDao().clear(date, taskId)
        if (done) db.beautyLogDao().insert(BeautyLog(date = date, taskId = taskId, done = true))
    }

    // Settings
    fun settings(): Flow<AppSettings?> = db.settingsDao().observe()
    suspend fun saveSettings(s: AppSettings) = db.settingsDao().save(s)

    // Exercise
    fun exerciseRange(from: Long, to: Long): Flow<List<ExerciseLog>> =
        db.exerciseDao().range(from, to)

    // Water
    fun waterToday(): Flow<Int> = db.waterDao().totalForDate(DateUtils.today())

    suspend fun addWater(ml: Int) {
        db.waterDao().insert(
            WaterLog(date = DateUtils.today(), ml = ml)
        )
    }

    suspend fun addExercise(name: String, rounds: Int, durationMin: Int, calories: Int) {
        db.exerciseDao().insert(
            ExerciseLog(
                dateTime = System.currentTimeMillis(),
                name = name,
                rounds = rounds,
                durationMin = durationMin,
                calories = calories
            )
        )
    }
}

object RepoProvider {
    fun get(context: Context) = Repository(AppDatabase.getInstance(context))
}
