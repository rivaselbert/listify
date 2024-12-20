package com.example.listify.ui.navigation

import androidx.navigation.NavHostController
import com.example.listify.data.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

class NavigationActions(private val navController: NavHostController) {

    fun navigateToUserDetails(user: User) {
        val userString = Json.encodeToString(user)
        val encodedUserString = URLEncoder.encode(userString, "UTF-8")

        navController.navigate(
            Route.USER_DETAILS
                .replace("{user}", encodedUserString)
        )
    }
}