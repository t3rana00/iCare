package com.example.icare.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [BeautyTask::class, BeautyLog::class, AppSettings::class, ExerciseLog::class, WaterLog::class],
    version = 3, // ⬅️ bump
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beautyTaskDao(): BeautyTaskDao
    abstract fun beautyLogDao(): BeautyLogDao
    abstract fun settingsDao(): SettingsDao
    abstract fun exerciseDao(): ExerciseLogDao
    abstract fun waterDao(): WaterLogDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "icare.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(dbx: SupportSQLiteDatabase) {
                            super.onCreate(dbx)
                            CoroutineScope(Dispatchers.IO).launch {
                                getInstance(context).prepopulate()
                            }
                        }
                    })
                    .build()
                INSTANCE = db
                db
            }
    }

    private suspend fun prepopulate() {
        val taskDao = beautyTaskDao()
        if (taskDao.countAll() == 0) {
            val defaults = listOf(
                // Skin
                BeautyTask(name = "Cleanser",     category = "Skin",  isDaily = true),
                BeautyTask(name = "Toner",        category = "Skin",  isDaily = true),
                BeautyTask(name = "Serum",        category = "Skin",  isDaily = true),
                BeautyTask(name = "Moisturizer",  category = "Skin",  isDaily = true),
                BeautyTask(name = "Sunscreen",    category = "Skin",  isDaily = true),
                BeautyTask(name = "Face Mask",    category = "Skin",  isDaily = false),

                // Hair
                BeautyTask(name = "Shampoo",      category = "Hair",  isDaily = false),
                BeautyTask(name = "Conditioner",  category = "Hair",  isDaily = false),
                BeautyTask(name = "Hair Oil",     category = "Hair",  isDaily = false),
                BeautyTask(name = "Hair Mask",    category = "Hair",  isDaily = false),

                // Body
                BeautyTask(name = "Body Lotion",  category = "Body",  isDaily = true),
                BeautyTask(name = "Body Scrub",   category = "Body",  isDaily = false),

                // Nails/Hands/Feet
                BeautyTask(name = "Hand Cream",   category = "Nails", isDaily = true),
                BeautyTask(name = "Foot Cream",   category = "Nails", isDaily = true),
                BeautyTask(name = "Nail Care",    category = "Nails", isDaily = false),
            )
            defaults.forEach { taskDao.insert(it) }
        }
        settingsDao().save(AppSettings())
    }

}
