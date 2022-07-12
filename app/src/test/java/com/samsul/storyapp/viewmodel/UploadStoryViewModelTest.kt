package com.samsul.storyapp.viewmodel

import androidx.paging.ExperimentalPagingApi
import com.samsul.storyapp.data.DataRepository
import com.samsul.storyapp.data.remote.model.upload.ResponseUploadStory
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
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UploadStoryViewModelTest {

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var uploadStoryViewModel: UploadStoryViewModel

    private val dummyToken = DataDummy.generateDummyToken()
    private val dummyUploadResponse = DataDummy.generateDummyFileUploadResponse()
    private val dummyMultipart = DataDummy.generateDummyMultipartFile()
    private val dummyDescription = DataDummy.generateDummyRequestBody()

    @Before
    fun setUp() {
        uploadStoryViewModel = UploadStoryViewModel(dataRepository)
    }

    @Test
    fun `Upload file successfully`() = runTest {
        val expectedResponse = flowOf(NetworkResult.Success(dummyUploadResponse))

        Mockito.`when`(
            uploadStoryViewModel.uploadStory(
                dummyToken,
                dummyDescription,
                "",
                "",
                dummyMultipart
            )
        ).thenReturn(expectedResponse)

        dataRepository.uploadStory(dummyToken, dummyDescription, "", "", dummyMultipart)
            .collect { result ->
                when(result){
                    is NetworkResult.Success -> {
                        assertNotNull(result.data)
                        assertTrue(true)
                        assertSame(dummyUploadResponse, result.data)
                    }
                    is NetworkResult.Error -> assertFalse(result.data!!.error)
                    is NetworkResult.Loading ->{}
                }
            }

        verify(dataRepository).uploadStory(dummyToken, dummyDescription, "", "", dummyMultipart)
    }

    @Test
    fun `Upload file Failed`() = runTest {
        val expectedResponse : Flow<NetworkResult<ResponseUploadStory>> = flowOf(NetworkResult.Error("failed"))

        Mockito.`when`(
            uploadStoryViewModel.uploadStory(
                dummyToken,
                dummyDescription,
                "",
                "",
                dummyMultipart
            )
        ).thenReturn(expectedResponse)

        dataRepository.uploadStory(dummyToken, dummyDescription, "", "", dummyMultipart)
            .collect { result ->
                when(result){
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

        verify(dataRepository).uploadStory(dummyToken, dummyDescription, "", "", dummyMultipart)
    }
}