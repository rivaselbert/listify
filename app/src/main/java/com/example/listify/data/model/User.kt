package com.example.listify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: UserName,
    val location: UserLocation,
    val email: String,
    val gender: String,
    val dob: UserDOB,
    val picture: UserPicture,
    val phone: String,
    val cell: String,
    val nat: String,
)

@Serializable
data class UserName(
    val title: String? = null,
    val first: String,
    val last: String,
) {

    val fullName: String
        get() = "$first $last"
}

@Serializable
data class UserLocation(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone: Timezone,
) {

    val fullAddress: String
        get() = "${street.number} ${street.name}, $city, $state, $country, $postcode"
}

@Serializable
data class Street(
    val number: Int,
    val name: String,
)

@Serializable
data class Coordinates(
    val latitude: String,
    val longitude: String,
)

@Serializable
data class Timezone(
    val offset: String,
    val description: String,
)

@Serializable
data class UserPicture(
    val large: String,
    val medium: String,
    val thumbnail: String,
)

@Serializable
data class UserDOB(
    val date: String,
    val age: Int,
)