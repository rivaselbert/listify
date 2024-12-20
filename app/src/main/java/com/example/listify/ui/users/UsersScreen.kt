package com.example.listify.ui.users

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listify.data.User
import com.example.listify.ui.components.textfield.SearchTextField
import com.example.listify.ui.theme.ListifyTheme
import com.example.listify.ui.users.components.UserItem
import com.example.listify.ui.users.components.UsersScreenTopBar
import com.example.listify.utils.TestDataFactory

@Composable
fun UsersScreen(
    navigateToUserDetails: (User) -> Unit,
) {
    UsersScreenContent(
        onUserItemClick = navigateToUserDetails
    )
}

@Composable
private fun UsersScreenContent(
    onUserItemClick: (User) -> Unit,
) {
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    SearchTextField(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        value = "Search",
                        onValueChange = {}
                    )
                }

                item {
                    UserItem(
                        user = TestDataFactory.allUsers.first(),
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
            onUserItemClick = {}
        )
    }
}