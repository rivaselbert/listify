package com.example.listify.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.listify.data.User
import com.example.listify.ui.navigation.NavigationActions
import com.example.listify.ui.navigation.Route
import com.example.listify.ui.users.UserDetailsScreen
import com.example.listify.ui.users.UsersScreen
import kotlinx.serialization.json.Json

@Composable
fun ListifyApp() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) { NavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Route.USERS
    ) {
        composable(Route.USERS) {
            UsersScreen(
                navigateToUserDetails = { user ->
                    navigationActions.navigateToUserDetails(user)
                }
            )
        }

        composable(Route.USER_DETAILS) { backStackEntry ->
            val userString = backStackEntry.arguments?.getString("user")
            val user = userString?.let { Json.decodeFromString<User>(it) }

            user?.let {
                UserDetailsScreen(
                    user = it,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}