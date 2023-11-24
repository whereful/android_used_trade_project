package com.hansung.androidusedtradeproject.model

import com.google.firebase.firestore.DocumentSnapshot

data class AppUser(
    val uid: String,
    val email: String,
    val name: String,
    val birth: String
) {

    constructor(doc: DocumentSnapshot) :
            this(
                uid = doc["uid"].toString(),
                email = doc["email"].toString(),
                name = doc["name"].toString(),
                birth = doc["birth"].toString()
            )

    fun makeHashMap(): HashMap<String, String> {
        return hashMapOf(
            "uid" to uid,
            "email" to email,
            "name" to name,
            "birth" to birth
        )
    }
}