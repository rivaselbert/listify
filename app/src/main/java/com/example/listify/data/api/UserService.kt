package com.example.listify.data.api

import com.example.listify.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("api/?results=30")
    suspend fun getUsers(): Response

    @GET("api/")
    suspend fun getPagedUsers(
        @Query("page") page: Int,
        @Query("results") results: Int,
        @Query("seed") seed: String?
    ): Response
}