package com.hansung.androidusedtradeproject.Service
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.androidusedtradeproject.model.SalesPost


class SalesPostService {
    companion object {
        val instance = SalesPostService()
    }

    val db: FirebaseFirestore = Firebase.firestore
    val postsCollectionRef = db.collection("posts")
    var uid = ""
        set(userUid) {
            field = userUid;
        }

    var email = ""
        set(userEmail) {
            field = userEmail
        }

    fun getPosts(): Task<QuerySnapshot> {
        return postsCollectionRef.get();
    }

    fun getPostsBySoldOut(soldOut : Boolean): Task<QuerySnapshot> {
        return postsCollectionRef.whereEqualTo("soldOut" , soldOut).get()
    }

    fun getPostById(id : String): Task<DocumentSnapshot> {
        return postsCollectionRef.document(id).get()
    }


    //#. onSuccess : 성공시 실행, onFailure : 실패시 실행
    fun uploadPost(
        title: String,
        content: String,
        price: Int,
        onSuccess: (() -> Unit)? = null,
        onFailure: (() -> Unit)? = null,
    )
    {
        if(Firebase.auth.currentUser == null){
            Log.v("로그", "인증 실패")
            return
        }

        postsCollectionRef.add(
            hashMapOf(
                "title" to title,
                "date" to Timestamp.now(),
                "content" to content,
                "price" to price,
                "soldOut" to false,
                "email" to Firebase.auth.currentUser!!.uid,
            )
        ).addOnSuccessListener {
            Log.v("로그", "업로드 완료")
            if(onSuccess != null) onSuccess()
        }.addOnFailureListener{
            Log.v("로그", "업로드 실패")
            if(onFailure != null) onFailure()
        }
    }

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

        postsCollectionRef.add(
            hashMapOf(
                "title" to title,
                "content" to content,
                "price" to price,
                "soldOut" to false,
                "writer" to Firebase.auth.currentUser!!.uid
            )
        ).addOnSuccessListener {
            Log.v("로그", "업로드 완료")
            if(onSuccess != null) onSuccess()
        }.addOnFailureListener{
            Log.v("로그", "업로드 실패")
            if(onFailure != null) onFailure()
        }
    }

    fun modifyPost(
        post : SalesPost,
        onSuccess: (() -> Void)? = null,
        onFailure: (() -> Void)? = null,
    ): Task<Void> {
        return postsCollectionRef.document(post.id).update(
            mapOf<String, Any>(
                "title" to post.title,
                "content" to post.content,
                "price" to post.price,
                "soldOut" to post.soldOut,
            )
        )
    }
}