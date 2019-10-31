package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.source.ChildrenDao
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ChildrenRepositoryTest {
    @Mock
    private lateinit var localChildrenDao: ChildrenDao

    @Mock
    private lateinit var remoteChildrenDao: ChildrenDao

    private lateinit var childrenRepository: ChildrenRepository

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
        childrenRepository = ChildrenRepository(localChildrenDao, remoteChildrenDao)
    }

    @Test
    fun `getAll 정상 케이스`() {
        `when`(localChildrenDao.getAll()).thenReturn(Single.just(childList))

        childrenRepository.getAll()
            .test()
            .assertValue(childList)
    }

    @Test
    fun `save 정상 케이스`() {
        `when`(localChildrenDao.save(child1)).thenReturn(Completable.complete())

        childrenRepository.save(child1)
            .test()
            .assertComplete()
    }
}