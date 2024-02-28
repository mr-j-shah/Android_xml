package com.crestinfosystems_jinay.trello.data


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserData(var email: String?, var name: String?, var mobileNumber: String?): Parcelable {
    fun toMap(): Map<String, Any> {
        return mapOf<String, Any>(
            "email" to email!!,
            "name" to name!!,
            "mobileNumber" to mobileNumber!!
        )
    }



    companion object {
        fun toObj(data: Map<String, Any>): UserData {
            return UserData(
                email = data["email"].toString() ?: null,
                name = data["email"].toString() ?: null,
                mobileNumber = data["email"].toString() ?: null
            )
        }
    }
}
