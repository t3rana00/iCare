package com.example.icare.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BeautyTask(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val category: String,      // "Skin", "Hair", "Body", "Nails"
    val isDaily: Boolean = true
)

@Entity
data class BeautyLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,          // yyyy-MM-dd
    val taskId: Long,
    val done: Boolean
)

@Entity
data class AppSettings(
    @PrimaryKey val key: Int = 0,
    val weightKg: Int = 57,
    val waterGoalMl: Int = 2000,
    val soundsEnabled: Boolean = true,
    val themeMode: String = "AUTO" // AUTO/LIGHT/DARK (future use)
)
@Entity
data class ExerciseLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateTime: Long,   // epoch millis
    val name: String,     // e.g., "Surya Namaskar"
    val rounds: Int,
    val durationMin: Int,
    val calories: Int
)
@Entity
data class WaterLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,   // yyyy-MM-dd
    val ml: Int
)
