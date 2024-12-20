package com.example.listify.data.model

class Response(
    val results: List<User> = emptyList(),
    val error: String? = null,
)