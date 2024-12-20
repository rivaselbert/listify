package com.example.listify.data.model

class Response(
    val results: List<User> = emptyList(),
    val info: ResponseInfo? = null,
    val error: String? = null,
)

data class ResponseInfo(
    val seed: String,
)