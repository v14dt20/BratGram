package com.example.bratgram.models

data class CommonModel(
    val id: String = "",
    var username: String = "",
    var fullname: String = "",
    var state: String = "",
    var phone: String = "",
    var photoUrl: String = "empty",
    var bio: String = ""
)