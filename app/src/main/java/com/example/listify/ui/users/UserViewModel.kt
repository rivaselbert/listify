package com.example.listify.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listify.data.model.User
import com.example.listify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUIState())
    val uiSate = _uiState.asStateFlow()

    private val _searchInput = MutableStateFlow("")

    init {
        // Fetch initial list of users
        getUsers()

        viewModelScope.launch {
            // Debouncing the search input to prevent frequent fetching of users
            _searchInput.debounce(timeoutMillis = 500)
                .collect {
                    Timber.d("Fetching users")
                    getUsers()
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
                    error = "Oops, something went wrong."
                )
            }
        }
    }

    fun updateSearchInput(input: String) {
        _searchInput.value = input
    }
}

data class UserUIState(
    val users: List<User> = emptyList(),
    val error: String? = null,
)