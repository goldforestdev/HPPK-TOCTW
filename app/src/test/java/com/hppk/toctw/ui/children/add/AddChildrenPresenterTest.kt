package com.hppk.toctw.ui.children.add

import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.Gender
import com.hppk.toctw.data.repository.ChildrenRepository
import io.reactivex.Completable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AddChildrenPresenterTest {
    @Mock
    private lateinit var view: AddChildrenContract.View

    @Mock
    private lateinit var childrenRepository: ChildrenRepository

    private val testScheduler: TestScheduler = TestScheduler()

    private lateinit var addChildrenPresenter: AddChildrenPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        addChildrenPresenter =
            AddChildrenPresenter(view, childrenRepository, testScheduler, testScheduler)
    }

    @Test
    fun `saveChild - 정상 케이스`() {
        val name = "tom"
        val gender = Gender.BOY
        val child = Child(name, gender)
        Mockito.`when`(childrenRepository.save(child)).thenReturn(Completable.complete())

        addChildrenPresenter.saveChild(name, gender)
        testScheduler.triggerActions()

        Mockito.verify(view).onChildAdded(child)
    }

}