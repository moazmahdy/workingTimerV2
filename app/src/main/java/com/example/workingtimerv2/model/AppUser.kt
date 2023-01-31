package com.example.workingtimerv2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppUser(
    var id: String? = null,
    var name: String? = null,
    val email: String? = null,
    var yesterday: Long? = 0L,
    var week: Long? = 0L,
    var month: Long? = 0L
) : Parcelable {
    companion object {
        const val COLLECTION_NAME = "users"
    }
}