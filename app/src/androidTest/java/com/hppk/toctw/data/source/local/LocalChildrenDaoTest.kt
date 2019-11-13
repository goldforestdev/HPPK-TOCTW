package com.hppk.toctw.data.source.local

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.hppk.toctw.data.model.Child
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalChildrenDaoTest{
    private lateinit var childrenDao: LocalChildrenDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        childrenDao = db.childrenDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeChildAndReadInList() {
        val child = Child("child A")

        childrenDao.save(child).test()
        childrenDao.getAll()
            .test()
            .assertValue {
                it.size == 1
            }

        childrenDao.save(Child("child B")).test()
        childrenDao.save(Child("child C")).test()
        childrenDao.save(Child("child D")).test()
        childrenDao.getAll()
            .test()
            .assertValue {
                it.size == 4
            }
    }
}