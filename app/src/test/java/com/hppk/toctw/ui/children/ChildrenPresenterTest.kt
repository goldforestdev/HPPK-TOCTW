package com.hppk.toctw.ui.children

import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.repository.ChildrenRepository
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ChildrenPresenterTest {
    @Mock
    private lateinit var view: ChildrenContract.View

    @Mock
    private lateinit var childrenRepository: ChildrenRepository

    private val testScheduler: TestScheduler = TestScheduler()

    private lateinit var childrenPresenter: ChildrenPresenter

    private val child1 = Child("child 1")
    private val child2 = Child("child 2")
    private val child3 = Child("child 3")
    private val child4 = Child("child 4")
    private val child5 = Child("child 5")

    private val childList: List<Child> = listOf(
        child1,
        child2,
        child3,
        child4,
        child5
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        childrenPresenter =
            ChildrenPresenter(view, childrenRepository, testScheduler, testScheduler)
    }

    @Test
    fun `getChildren - Children이 존재하면 onChildrenLoaded가 호출되어야 한다`() {
        `when`(childrenRepository.getAll()).thenReturn(Single.just(childList))

        childrenPresenter.getChildren()
        testScheduler.triggerActions()

        verify(view).onChildrenLoaded(childList)
    }
}