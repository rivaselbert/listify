package com.example.listify.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.listify.data.api.ApiException
import com.example.listify.data.api.UserService
import com.example.listify.data.model.User
import timber.log.Timber

class UsersPagingSource(
    private val userService: UserService,
) : PagingSource<Int, User>() {

    var seed: String? = null

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1

        return try {
            val response = userService.getPagedUsers(
                page = page,
                results = params.loadSize,
                seed = seed
            )

            if (response.error != null) {
                throw ApiException(response.error)
            }

            if (seed == null && response.info?.seed != null) {
                seed = response.info.seed
            }

            val nextKey = if (response.results.isNotEmpty()) {
                page + 1
            } else {
                null
            }

            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: ApiException) {
            Timber.e(e, "ApiException: An error occurred during getPagedUsers")
            LoadResult.Error(e)
        } catch (e: Exception) {
            Timber.e(e, "An error occurred during getPagedUsers")
            LoadResult.Error(e)
        }
    }
}