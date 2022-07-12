package com.samsul.storyapp.viewmodel

import com.samsul.storyapp.data.DataRepository
import com.samsul.storyapp.data.remote.model.login.ResponseLogin
import com.samsul.storyapp.data.remote.model.register.ResponseRegister
import com.samsul.storyapp.utils.CoroutinesTestRule
import com.samsul.storyapp.utils.DataDummy
import com.samsul.storyapp.utils.NetworkResult
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginAndRegisterViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var dataRepository: DataRepository
    private lateinit var loginRegisterViewModel: LoginAndRegisterViewModel

    private val dummyLoginResponse = DataDummy.generateDummyLoginResponse()
    private val dummyRegisterResponse = DataDummy.generateDummyRegisterResponse()
    private val dummyName = "Samsul"
    private val dummyEmail = "sam@mail.com"
    private val dummyPassword = "123456"

    @Before
    fun setup() {
        loginRegisterViewModel = LoginAndRegisterViewModel(dataRepository)
    }

    @Test
    fun `Login is successfully - NetworkResult Success`(): Unit = runTest {
        val expectedResponse = flowOf(NetworkResult.Success(dummyLoginResponse))

        `when`(loginRegisterViewModel.login(dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        loginRegisterViewModel.login(dummyEmail, dummyPassword).collect { result ->
            when(result) {
                is NetworkResult.Success -> {
                    assertTrue(true)
                    assertNotNull(result.data)
                    assertSame(result.data, dummyLoginResponse)

                }
                is NetworkResult.Error -> {
                    assertFalse(result.data!!.error)
                }

                is NetworkResult.Loading ->{}
            }
        }
        verify(dataRepository).login(dummyEmail, dummyPassword)
    }

    @Test
    fun `Login is Failed - NetworkResult Failed`(): Unit = runTest {
        val expectedResponse : Flow<NetworkResult<ResponseLogin>> = flowOf(NetworkResult.Error("failed"))

        `when`(loginRegisterViewModel.login(dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        loginRegisterViewModel.login(dummyEmail, dummyPassword).collect { result ->
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
        verify(dataRepository).login(dummyEmail, dummyPassword)
    }

    @Test
    fun `Register is successfully - NetworkResult Success`(): Unit = runTest {
        val expectedResponse = flowOf(NetworkResult.Success(dummyRegisterResponse))

        `when`(loginRegisterViewModel.register(dummyName, dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        loginRegisterViewModel.register(dummyName, dummyEmail, dummyPassword).collect { result ->
            when(result) {
                is NetworkResult.Success -> {
                    assertTrue(true)
                    assertNotNull(result.data)
                    assertSame(result.data, dummyRegisterResponse)

                }
                is NetworkResult.Error -> {
                    assertFalse(result.data!!.error)
                }

                is NetworkResult.Loading ->{}
            }
        }
        verify(dataRepository).register(dummyName, dummyEmail, dummyPassword)
    }

    @Test
    fun `Register is Failed - NetworkResult Failed`(): Unit = runTest {
        val expectedResponse : Flow<NetworkResult<ResponseRegister>> = flowOf(NetworkResult.Error("failed"))

        `when`(loginRegisterViewModel.register(dummyName,dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        loginRegisterViewModel.register(dummyName, dummyEmail, dummyPassword).collect { result ->
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
        verify(dataRepository).register(dummyName,dummyEmail, dummyPassword)
    }


}