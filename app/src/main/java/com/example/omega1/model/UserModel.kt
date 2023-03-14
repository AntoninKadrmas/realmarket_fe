package com.example.omega1.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    val createdIn: String,
    val email: String,
    val first_name: String,
    val cardId: String,
    val last_name: String,
    val password: String,
    val phone: String,
    val validated: Validated
)
data class UserModelLogin(
    val cardId: String,
    val password: String,
)
data class UserTokenAuth(
    @SerializedName("token")
    val token:String,
    @SerializedName("expirationTime")
    val expirationTime:String
)