package com.hppk.toctw.data.source.local

import android.content.Context
import androidx.room.*
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.Gender

private const val DB = "hppk_toctw.db"

@Database(
    entities = [Child::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun childrenDao(): LocalChildrenDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB
                    ).build()
                }

                return INSTANCE!!
            }
        }
    }

}

class Converters {
    @TypeConverter
    fun stringToGender(s: String?): Gender = s?.let { Gender.valueOf(it) } ?: Gender.BOY

    @TypeConverter
    fun genderToString(gender: Gender): String = gender.name
}