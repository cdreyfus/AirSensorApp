package fr.cdreyfus.airqualitysensorapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val id: String, val name: String, val username: String, val aioKey: String) :
    Parcelable

data class UserResponse(
    @SerializedName("user") val user: User
)
