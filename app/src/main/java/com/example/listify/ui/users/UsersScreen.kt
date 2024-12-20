package com.example.listify.ui.users

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.listify.data.model.User
import com.example.listify.ui.components.textfield.SearchTextField
import com.example.listify.ui.theme.ListifyTheme
import com.example.listify.ui.users.components.UserItem
import com.example.listify.ui.users.components.UsersScreenTopBar

@Composable
fun UsersScreen(
    navigateToUserDetails: (User) -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiSate.collectAsStateWithLifecycle()
    
    UsersScreenContent(
        uiState = uiState,
        onUserItemClick = navigateToUserDetails,
        onSearchTextValueChange = viewModel::updateSearchInput
    )
}

@Composable
private fun UsersScreenContent(
    uiState: UserUIState,
    onUserItemClick: (User) -> Unit,
    onSearchTextValueChange: (String) -> Unit,
) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            UsersScreenTopBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    SearchTextField(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        value = searchText,
                        onValueChange = {
                            searchText = it
                            onSearchTextValueChange(it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }

                items(uiState.users) { user ->
                    UserItem(
                        user = user,
                        onClick = onUserItemClick
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
private fun UsersScreenPreview() {
    ListifyTheme {
        UsersScreenContent(
            uiState = UserUIState(),
            onUserItemClick = {},
            onSearchTextValueChange = {}
        )
    }
}