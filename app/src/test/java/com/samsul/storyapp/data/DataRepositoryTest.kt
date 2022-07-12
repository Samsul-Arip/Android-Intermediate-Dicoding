package com.samsul.storyapp.data

import com.samsul.storyapp.utils.CoroutinesTestRule
import com.samsul.storyapp.utils.DataDummy
import com.samsul.storyapp.utils.NetworkResult
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DataRepositoryTest {
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var dataSource: DataSource
    private lateinit var dataRepository: DataRepository
    private val dummyToken = DataDummy.generateDummyToken()
    private val dummyMultipart = DataDummy.generateDummyMultipartFile()
    private val dummyDescription = DataDummy.generateDummyRequestBody()

    private val dummyName = "Samsul"
    private val dummyEmail = "sam@mail.com"
    private val dummyPassword = "123456"

    @Before
    fun setup() {
        dataRepository = DataRepository(dataSource)
    }

    @Test
    fun `Login successfully`(): Unit = runTest {
        val expectedResponse = DataDummy.generateDummyLoginResponse()

        `when`(dataSource.login(dummyEmail, dummyPassword)).thenReturn(Response.success(expectedResponse))

        dataRepository.login(dummyEmail, dummyPassword).collect { result ->

            when(result) {
                is NetworkResult.Success -> {
                    assertTrue(true)
                    assertNotNull(result.data)
                    assertEquals(expectedResponse, result.data)
                }
                is NetworkResult.Error -> {
                    assertFalse(result.data!!.error)
                    assertNull(result)
                }

                is NetworkResult.Loading ->{}
            }
        }
    }

    @Test
    fun `Login Failed`(): Unit = runTest {
        `when`(
            dataSource.login(
                dummyEmail,
                dummyPassword
            )
        ).then { throw Exception() }

        dataRepository.login(dummyEmail, dummyPassword).collect { result ->
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
    }

    @Test
    fun `Register successfully`(): Unit = runTest {
        val expectedResponse = DataDummy.generateDummyRegisterResponse()

        `when`(dataSource.register(dummyName, dummyEmail, dummyPassword)).thenReturn(Response.success(expectedResponse))

        dataRepository.register(dummyName, dummyEmail, dummyPassword).collect { result ->

            when(result) {
                is NetworkResult.Success -> {
                    assertTrue(true)
                    assertNotNull(result.data)
                    assertEquals(expectedResponse, result.data)
                }
                is NetworkResult.Error -> {
                    assertFalse(result.data!!.error)
                    assertNull(result)
                }

                is NetworkResult.Loading ->{}
            }
        }
    }

    @Test
    fun `User register failed`(): Unit = runTest {
        `when`(
            dataSource.register(
                dummyName,
                dummyEmail,
                dummyPassword
            )
        ).then { throw Exception() }

        dataRepository.register(dummyName, dummyEmail, dummyPassword).collect { result ->
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
    }

    @Test
    fun `Upload image file - successfully`() = runTest {
        val expectedResponse = DataDummy.generateDummyFileUploadResponse()

        `when`(
            dataSource.uploadStory(
                "Bearer $dummyToken",
                "text",
                "",
                "",
                dummyMultipart
            )
        ).thenReturn(Response.success(expectedResponse))

        dataRepository.uploadStory(dummyToken, "text", "", "",dummyMultipart)
            .collect { result ->
                when(result){
                    is NetworkResult.Success -> {
                        assertNotNull(result.data)
                        assertTrue(true)
                    }
                    is NetworkResult.Error -> {}
                    is NetworkResult.Loading ->{}
                }
            }

        verify(dataSource)
            .uploadStory(
                "Bearer $dummyToken",
                "text",
                "",
                "",
                dummyMultipart
            )
    }

    @Test
    fun `Upload image file - throw exception`() = runTest {

        `when`(
            dataSource.uploadStory(
                "Bearer $dummyToken",
                dummyDescription,
                "",
                "",
                dummyMultipart
            )
        ).then { throw Exception() }

        dataRepository.uploadStory(dummyToken, dummyDescription, "", "",dummyMultipart)
            .collect { result ->
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

        verify(dataSource).uploadStory(
            "Bearer $dummyToken",
            dummyDescription,
            "",
            "",
            dummyMultipart
        )
    }
}