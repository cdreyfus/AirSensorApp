package fr.cdreyfus.airqualitysensorapp.ui.login

import com.google.gson.annotations.SerializedName

data class User(val id: String, val name: String, val username: String)

data class UserResponse(
    @SerializedName("user") val user: User
)
