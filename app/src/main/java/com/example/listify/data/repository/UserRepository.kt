package com.example.listify.data.repository

import com.example.listify.data.api.ApiException
import com.example.listify.data.api.UserService
import com.example.listify.data.model.User
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService
) {

    /**
     * Fetches a list of random users.
     */
    suspend fun getUsers(): Result<List<User>> {
        return try {
            val response = userService.getUsers()

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
}