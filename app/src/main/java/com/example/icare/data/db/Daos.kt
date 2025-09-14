package com.example.icare.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BeautyTaskDao {
    @Query("SELECT * FROM BeautyTask WHERE category=:category ORDER BY id")
    fun tasksByCategory(category: String): Flow<List<BeautyTask>>

    @Insert suspend fun insert(task: BeautyTask): Long
    @Query("SELECT COUNT(*) FROM BeautyTask")
    suspend fun countAll(): Int
    @Query("SELECT * FROM BeautyTask")
    suspend fun all(): List<BeautyTask>
}

@Dao
interface BeautyLogDao {
    @Query("SELECT * FROM BeautyLog WHERE date=:date")
    fun logsForDate(date: String): Flow<List<BeautyLog>>

    @Insert suspend fun insert(log: BeautyLog): Long

    @Query("DELETE FROM BeautyLog WHERE date=:date AND taskId=:taskId")
    suspend fun clear(date: String, taskId: Long)
}

@Dao
interface SettingsDao {
    @Query("SELECT * FROM AppSettings WHERE `key`=0")
    fun observe(): Flow<AppSettings?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(settings: AppSettings)
}
@Dao
interface ExerciseLogDao {
    @Query("SELECT * FROM ExerciseLog WHERE dateTime BETWEEN :from AND :to ORDER BY dateTime DESC")
    fun range(from: Long, to: Long): Flow<List<ExerciseLog>>

    @Insert suspend fun insert(log: ExerciseLog): Long
}
@Dao
interface WaterLogDao {
    @Query("SELECT IFNULL(SUM(ml), 0) FROM WaterLog WHERE date=:date")
    fun totalForDate(date: String): Flow<Int>

    @Insert
    suspend fun insert(log: WaterLog): Long
}
