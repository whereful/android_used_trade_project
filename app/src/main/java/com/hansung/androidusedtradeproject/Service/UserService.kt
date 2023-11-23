package com.hansung.androidusedtradeproject.Service

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.androidusedtradeproject.model.AppUser

object UserService{
    val usersCollectionRef = Firebase.firestore.collection("users")

    fun getUserDataByEmail(email : String) : Task<QuerySnapshot> {
        return usersCollectionRef.whereEqualTo("email", email).get()
    }

    fun getUserDataByUid(uid : String) : Task<DocumentSnapshot> {
        return usersCollectionRef.document(uid).get()
    }

    fun setUserData(appUser : AppUser) : Task<Void> {
        return usersCollectionRef.document(appUser.uid).set(appUser.makeHashMap())
    }


}