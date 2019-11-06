package com.hppk.toctw.ui.stamps.qrcamera

import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.ChildStampJoin
import com.hppk.toctw.data.repository.StampRepository
import io.reactivex.Completable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class QRCodePresenterTest {
    @Mock
    private lateinit var view: QRCodeContract.View

    @Mock
    private lateinit var stampRepository: StampRepository

    private val testScheduler: TestScheduler = TestScheduler()

    private lateinit var qrCodePresenter: QRCodePresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        qrCodePresenter = QRCodePresenter(view, stampRepository, testScheduler, testScheduler)
    }

    @Test
    fun `saveStampTest`() {
        val child = Child("childName", 0)
        val boothId = "boothId"

        `when`(stampRepository.saveChildStampJoin(ChildStampJoin(child.name, boothId))).thenReturn(Completable.complete())

        qrCodePresenter.saveStamp(child, boothId)
        testScheduler.triggerActions()

        verify(view).onStampSaved()
    }
}