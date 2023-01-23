package com.example.workingtimerv2.database

import com.example.workingtimerv2.model.AppUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun getCollection (collectionName:String): CollectionReference {
    val db = Firebase.firestore
    return  db.collection(collectionName)
}
fun addUserToFirestore(user: AppUser,
                       onSuccessListener: OnSuccessListener<Void>,
                       onFailureListener: OnFailureListener
){
    val userCollection = getCollection(AppUser.COLLECTION_NAME)
    val userDoc = userCollection.document(user.id!!)
    userDoc.set(user)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}