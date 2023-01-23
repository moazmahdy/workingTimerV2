package com.example.workingtimerv2.model

data class AppUser(
    var id: String? = null,
    var name: String? = null,
    val email: String?= null
)
{
    companion object{
        const val COLLECTION_NAME = "users"
    }
}
