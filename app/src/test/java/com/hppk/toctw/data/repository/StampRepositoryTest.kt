package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Stamp
import com.hppk.toctw.data.source.impl.FirestoreBoothDao
import com.hppk.toctw.data.source.local.LocalChildStampDao
import com.hppk.toctw.data.source.local.LocalStampDao
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class StampRepositoryTest {
    @Mock
    private lateinit var localStampDao: LocalStampDao

    @Mock
    private lateinit var localChildStampDao: LocalChildStampDao

    @Mock
    private lateinit var remoteBoothDao: FirestoreBoothDao

    private lateinit var stampRepository: StampRepository

    private val booth1 = Booth("1", "booth1", isStamp = true)
    private val booth2 = Booth("2", "booth2", isStamp = true)
    private val booth3 = Booth("3", "booth3", isStamp = true)
    private val boothList = listOf(booth1, booth2, booth3)

    private val stamp1 = Stamp("1", "booth1")
    private val stamp2 = Stamp("2", "booth2")
    private val stamp3 = Stamp("3", "booth3")
    private val stampList = listOf(stamp1, stamp2, stamp3)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        stampRepository = StampRepository(localStampDao, localChildStampDao, remoteBoothDao)
    }

    @Test
    fun `getStamps - 캐시된 데이터가 없으면 Firebase에서 데이터를 가져와 캐시해야 한다`() {
        `when`(localStampDao.getAll()).thenReturn(Single.just(listOf()))
        `when`(remoteBoothDao.getStampBoothList()).thenReturn(Single.just(boothList))
        `when`(localStampDao.save(*stampList.toTypedArray())).thenReturn(Completable.complete())

        stampRepository.getStamps()
            .test()
            .assertValue(stampList)
    }

}