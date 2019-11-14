package com.hppk.toctw.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.ChildStampJoin
import com.hppk.toctw.data.model.Favorites
import com.hppk.toctw.data.model.Stamp

private const val DB = "hppk_toctw.db"

@Database(
    entities = [Child::class, Stamp::class, ChildStampJoin::class, Favorites::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun childrenDao(): LocalChildrenDao

    abstract fun stampDao(): LocalStampDao

    abstract fun childStampDao(): LocalChildStampDao

    abstract fun favoritesDao() : LocalFavoritesDao

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
