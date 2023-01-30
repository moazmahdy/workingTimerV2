package com.example.workingtimerv2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppUser(
    var id: String? = null,
    var name: String? = null,
    var email: String?= null,
    var dayHour: String?= null,
    var weekHour: String?= null,
    var monthHour: String?= null
):Parcelable
{
    companion object{
        const val COLLECTION_NAME = "users"
    }
}
