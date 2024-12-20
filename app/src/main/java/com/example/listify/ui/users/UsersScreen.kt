package com.example.listify.ui.users

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.listify.R
import com.example.listify.data.model.User
import com.example.listify.ui.components.ListLoader
import com.example.listify.ui.components.textfield.SearchTextField
import com.example.listify.ui.theme.ListifyTheme
import com.example.listify.ui.users.components.UserItem
import com.example.listify.ui.users.components.UsersScreenTopBar
import kotlinx.coroutines.flow.flowOf

@Composable
fun UsersScreen(
    navigateToUserDetails: (User) -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiSate.collectAsStateWithLifecycle()
    val pagingUsers = viewModel.pagingUsers.collectAsLazyPagingItems()
    
    UsersScreenContent(
        uiState = uiState,
        pagingUsers = pagingUsers,
        onUserItemClick = navigateToUserDetails,
        onSearchTextValueChange = viewModel::updateSearchInput,
        onSetPaginationEnabled = viewModel::setPaginationEnabled,
    )
}

@Composable
private fun UsersScreenContent(
    uiState: UserUIState,
    pagingUsers: LazyPagingItems<User>,
    onUserItemClick: (User) -> Unit,
    onSearchTextValueChange: (String) -> Unit,
    onSetPaginationEnabled: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    var isPaginationEnabled by remember { mutableStateOf(uiState.isPaginationEnabled) }

    val refresh = pagingUsers.loadState.refresh

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
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SearchTextField(
                            modifier = Modifier.weight(1f),
                            value = searchText,
                            onValueChange = {
                                searchText = it
                                onSearchTextValueChange(it)
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )

                        Switch(
                            checked = isPaginationEnabled,
                            onCheckedChange = {
                                isPaginationEnabled = it
                                onSetPaginationEnabled(it)
                            }
                        )

                        Text(stringResource(R.string.paginate))
                    }
                }

                if (uiState.isPaginationEnabled) {
                    items(pagingUsers) { user ->
                        user?.let {
                            UserItem(
                                user = user,
                                onClick = onUserItemClick
                            )
                        }
                    }
                } else {
                    items(uiState.users) { user ->
                        UserItem(
                            user = user,
                            onClick = onUserItemClick
                        )
                    }
                }

                if (uiState.isPaginationEnabled && refresh is LoadState.Loading) {
                    item {
                        ListLoader()
                    }
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
            pagingUsers = flowOf(PagingData.empty<User>()).collectAsLazyPagingItems(),
            onUserItemClick = {},
            onSearchTextValueChange = {},
            onSetPaginationEnabled = {}
        )
    }
}