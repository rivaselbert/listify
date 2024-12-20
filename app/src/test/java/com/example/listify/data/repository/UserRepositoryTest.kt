package com.example.listify.data.repository

import com.example.listify.data.api.ApiException
import com.example.listify.data.model.Response
import com.example.listify.data.source.remote.RemoteUserDataSource
import com.example.listify.utils.TestDataFactory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    @Mock private lateinit var remoteUserDataSource: RemoteUserDataSource

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = UserRepository(
            remoteUserDataSource = remoteUserDataSource
        )
    }

    @Test
    fun `getUsers success`() = runBlocking {
        val response = Response(
            results = TestDataFactory.allUsers
        )

        val expectedResult = Result.success(response.results)

        Mockito.`when`(remoteUserDataSource.getUsers()).thenReturn(response)

        val result = userRepository.getUsers()

        verify(remoteUserDataSource).getUsers()
        assert(result.isSuccess)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getUsers should return failure when response returns error`() = runBlocking {
        val response = Response(
            error = "Uh oh, something has gone wrong."
        )

        val expectedResult = Result.failure<ApiException>(ApiException(response.error!!))

        Mockito.`when`(remoteUserDataSource.getUsers()).thenReturn(response)

        val result = userRepository.getUsers()

        verify(remoteUserDataSource).getUsers()
        assert(result.isFailure)
        assertEquals(expectedResult.getOrNull(), result.getOrNull())
    }

    @Test
    fun `getUsers should return failure when api throws exception`() = runBlocking {
        Mockito.`when`(remoteUserDataSource.getUsers()).thenThrow()

        val result = userRepository.getUsers()

        verify(remoteUserDataSource).getUsers()
        assert(result.isFailure)
    }
}