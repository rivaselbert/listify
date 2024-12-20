package com.example.listify.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.listify.data.model.User
import com.example.listify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUIState())
    val uiSate = _uiState.asStateFlow()

    private val _pagingUsers = MutableStateFlow<PagingData<User>>(PagingData.empty())
    val pagingUsers = _pagingUsers.asStateFlow()

    private val _searchInput = MutableStateFlow("")

    init {
        viewModelScope.launch {
            // Debouncing the search input to prevent frequent fetching of users
            _searchInput.debounce(timeoutMillis = 500)
                .collect {
                    if (uiSate.value.isPaginationEnabled) {
                        getPagedUsers()
                    } else {
                        getUsers()
                    }
                }
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            val result = userRepository.getUsers()

            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    users = result.getOrNull() ?: emptyList()
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    error = GENERIC_ERROR_MESSAGE
                )
            }
        }
    }

    private fun getPagedUsers() {
        viewModelScope.launch {
            userRepository
                .getPagedUsers()
                .cachedIn(viewModelScope)
                .collect {
                    _pagingUsers.value = it
                }
        }
    }

    fun updateSearchInput(input: String) {
        _searchInput.value = input
    }


    /*
    * Switching between paginated and non-paginated users is for the coding challenge purposes only.
    * In a real application, non-paginated fetching is unnecessary and pagination should be
    * the default approach.
    * */
    // TODO: Implement logic to retain the current data when pagination is disabled or enabled.
    fun setPaginationEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(
            isPaginationEnabled = enabled
        )

        if (enabled) {
            getPagedUsers()
        } else {
            getUsers()
        }
    }

    companion object {
        private const val GENERIC_ERROR_MESSAGE = "Oops, something went wrong."
    }
}

data class UserUIState(
    val users: List<User> = emptyList(),
    val isPaginationEnabled: Boolean = false,
    val error: String? = null,
)