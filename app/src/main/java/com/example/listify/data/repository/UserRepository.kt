package com.example.listify.data.repository

import com.example.listify.data.api.ApiException
import com.example.listify.data.model.User
import com.example.listify.data.source.remote.RemoteUserDataSource
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
) {

    suspend fun getUsers(): Result<List<User>> {
        return try {
            val response = remoteUserDataSource.getUsers()

            if (response.error != null) {
                throw ApiException(response.error)
            }

            Result.success(response.results)
        } catch (e: ApiException) {
            Timber.e(e, "ApiException: An error occurred during getUsers")
            Result.failure(e)
        } catch (e: Exception) {
            Timber.e(e, "An error occurred during getUsers")
            Result.failure(e)
        }
    }

    fun getPagedUsers() = remoteUserDataSource.getPagedUsers()
}