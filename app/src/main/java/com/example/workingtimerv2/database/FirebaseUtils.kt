package com.example.workingtimerv2.database

import com.example.workingtimerv2.model.AppUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
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
fun signIn(uid: String,
           onSuccessListener: OnSuccessListener<DocumentSnapshot>,
           onFailureListener: OnFailureListener
){
    val db = Firebase.firestore
    val userCollection = db.collection(AppUser.COLLECTION_NAME)
    userCollection.document(uid)
        .get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getUsers(onSuccessListener: OnSuccessListener<QuerySnapshot>,
             onFailureListener: OnFailureListener){
    val collection = getCollection(AppUser.COLLECTION_NAME)
    collection.get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}