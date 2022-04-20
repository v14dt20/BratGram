package com.example.bratgram.models

data class CommonModel(
    val id: String = "",
    var username: String = "",
    var fullname: String = "",
    var state: String = "",
    var phone: String = "",
    var photoUrl: String = "empty",
    var bio: String = "",

    var text: String = "",
    var type: String = "",
    var from: String = "",
    var timeStamp: Any = "",
    var imageUrl: String = "empty"
){
    override fun equals(other: Any?): Boolean {
        return (other as CommonModel).id == id
    }
}