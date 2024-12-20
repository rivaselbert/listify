package com.example.listify.extension

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toDisplayDate(): String {
    val offsetDateTime = OffsetDateTime.parse(this)
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)
    return formatter.format(offsetDateTime)
}