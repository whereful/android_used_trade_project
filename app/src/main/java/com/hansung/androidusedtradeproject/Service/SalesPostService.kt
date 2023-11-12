package com.hansung.androidusedtradeproject.Service
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SalesPostService {
    val db: FirebaseFirestore = Firebase.firestore
    val itemsCollectionRef = db.collection("items")

    fun getPosts(): Task<QuerySnapshot> {
        return itemsCollectionRef.get();
    }

    fun getPostsBySoldOut(soldOut : Boolean): Task<QuerySnapshot> {
        return itemsCollectionRef.whereEqualTo("soldOut" , soldOut).get()
    }

    
    //#. onSuccess : 성공시 실행, onFailure : 실패시 실행
    fun addPost(
        title: String,
        content: String,
        price: Int,
        onSuccess: (() -> Void)? = null,
        onFailure: (() -> Void)? = null,
        )
    {
        if(Firebase.auth.currentUser == null){
            Log.v("로그", "인증 실패")
            return
        }

        itemsCollectionRef.add(
            hashMapOf(
                "title" to title,
                "content" to content,
                "price" to price,
                "soldOut" to false,
                "uid" to Firebase.auth.currentUser!!.uid
            )
        ).addOnSuccessListener {
            Log.v("로그", "업로드 완료")
            if(onSuccess != null) onSuccess()
        }.addOnFailureListener{
            Log.v("로그", "업로드 실패")
            if(onFailure != null) onFailure()
        }
    }
}