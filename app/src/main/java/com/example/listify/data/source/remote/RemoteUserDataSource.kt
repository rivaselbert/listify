package com.example.listify.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.listify.data.api.UserService
import com.example.listify.data.model.User
import com.example.listify.data.paging.UsersPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val userService: UserService
) {

    /**
     * Fetches a list of random users.
     */
    suspend fun getUsers() = userService.getUsers()

    fun getPagedUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = {
                UsersPagingSource(userService).apply {
                    seed = null
                }
            }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}