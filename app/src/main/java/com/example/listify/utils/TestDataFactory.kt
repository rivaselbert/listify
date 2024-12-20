package com.example.listify.utils

import com.example.listify.data.Coordinates
import com.example.listify.data.Street
import com.example.listify.data.Timezone
import com.example.listify.data.User
import com.example.listify.data.UserDOB
import com.example.listify.data.UserLocation
import com.example.listify.data.UserName
import com.example.listify.data.UserPicture

object TestDataFactory {

    private val user1 = User(
        name = UserName(
            title = "Mr",
            first = "William",
            last = "Wilson"
        ),
        location = UserLocation(
            street = Street(
                number = 2550,
                name = "Devonport Road"
            ),
            city = "Taupo",
            state = "Wellington",
            country = "New Zealand",
            postcode = 38193,
            coordinates = Coordinates(
                latitude = "53.3370",
                longitude = "-21.0851"
            ),
            timezone = Timezone(
                offset = "0:00",
                description = "Western Europe Time, London, Lisbon, Casablanca"
            )
        ),
        email = "william.wilson@example.com",
        picture = UserPicture(
            large = "https://randomuser.me/api/portraits/men/39.jpg",
            medium = "https://randomuser.me/api/portraits/med/men/39.jpg",
            thumbnail = "https://randomuser.me/api/portraits/thumb/men/39.jpg"
        ),
        phone = "(006)-817-9191",
        cell = "",
        dob = UserDOB(
            date = "1952-09-01T10:03:54.744Z",
            age = 72
        ),
        gender = "male",
        nat = "NZ"
    )

    val allUsers = listOf(
        user1
    )
}