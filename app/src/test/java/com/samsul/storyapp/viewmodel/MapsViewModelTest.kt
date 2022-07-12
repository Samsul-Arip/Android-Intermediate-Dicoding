package com.samsul.storyapp.viewmodel

import androidx.paging.ExperimentalPagingApi
import com.samsul.storyapp.data.DataRepository
import com.samsul.storyapp.data.remote.model.home.ResponseHome
import com.samsul.storyapp.utils.DataDummy
import com.samsul.storyapp.utils.NetworkResult
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {

    @Mock
    private lateinit var dataRepository: DataRepository
    private lateinit var mapsViewModel: MapsViewModel

    private val dummyStoriesResponse = DataDummy.generateDummyStoriesResponse()
    private val dummyToken = DataDummy.generateDummyToken()

    @Before
    fun setup() {
        mapsViewModel = MapsViewModel(dataRepository)
    }

    @Test
    fun `Get location story is successfully - NetworkResult Success`(): Unit = runTest {
        val expectedResponse = flowOf(NetworkResult.Success(dummyStoriesResponse))

       `when`(mapsViewModel.getStoriesLocation(dummyToken)).thenReturn(expectedResponse)

        mapsViewModel.getStoriesLocation(dummyToken).collect { result ->
            when(result) {
                is NetworkResult.Success -> {
                    assertTrue(true)
                    assertNotNull(result.data)
                    assertSame(result.data, dummyStoriesResponse)

                }
                is NetworkResult.Error -> {
                    assertFalse(result.data!!.error)
                }

                is NetworkResult.Loading ->{}
            }
        }

        verify(dataRepository).getStoriesLocation(dummyToken)
    }

    @Test
    fun `Get location story is failed - NetworkResult Error`(): Unit = runTest {
        val expectedResponse : Flow<NetworkResult<ResponseHome>> = flowOf(NetworkResult.Error("failed"))

        `when`(mapsViewModel.getStoriesLocation(dummyToken)).thenReturn(expectedResponse)

        mapsViewModel.getStoriesLocation(dummyToken).collect { result ->
            when(result) {
                is NetworkResult.Success -> {
                    assertTrue(false)
                    assertFalse(result.data!!.error)
                }
                is NetworkResult.Error -> {
                    assertNotNull(result.message)
                }

                is NetworkResult.Loading ->{}
            }
        }

        verify(dataRepository).getStoriesLocation(dummyToken)
    }


}