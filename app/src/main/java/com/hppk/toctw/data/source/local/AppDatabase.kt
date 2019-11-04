package com.hppk.toctw.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.ChildStampJoin
import com.hppk.toctw.data.model.StampBooth

private const val DB = "hppk_toctw.db"

@Database(
    entities = [Child::class, StampBooth::class, ChildStampJoin::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun childrenDao(): LocalChildrenDao

    abstract fun stampDao(): LocalStampBoothDao

    abstract fun childStampDao(): LocalChildStampDao

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
