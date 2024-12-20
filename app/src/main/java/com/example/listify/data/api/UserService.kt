package com.example.listify.data.api

import com.example.listify.data.model.Response
import retrofit2.http.GET

interface UserService {

    @GET("api/?results=10")
    suspend fun getUsers(): Response
}