package com.hansung.androidusedtradeproject.Service

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.androidusedtradeproject.model.SalesPost

object MessageService {

    val messagesCollectionRef = Firebase.firestore.collection("messages")

    fun getUsersMessage() : Task<QuerySnapshot>?{
        if(Firebase.auth.currentUser != null){
            return messagesCollectionRef.whereEqualTo("receiverEmail", Firebase.auth.currentUser!!.email).get()
        }
        Log.v("로그" , "[MessageService.getUsersMessage()] 로그인 되어있지 않음.")
        return null;
    }

    fun getMessagesByReceiverEmail(email : String): Task<QuerySnapshot> {
        return messagesCollectionRef.whereEqualTo("receiverEmail", email).get()
    }

    //#. fromPostId는 아직은 사용하지 않으니 아무값이나 넣어도됩니다.
    fun sendMessage(
        content : String,
        receiverEmail: String,
        fromPostId : String,
        onSuccess: (() -> Unit)? = null,
        onFailure: (() -> Unit)? = null,
        onAuthFailure : (() -> Unit)? = null
    ){
        if(Firebase.auth.currentUser == null){
            Log.v("로그" , "로그인이 되어있지 않습니다.")
            if (onAuthFailure != null) onAuthFailure()
            return
        }

        messagesCollectionRef.add(hashMapOf(
            "senderEmail" to Firebase.auth.currentUser!!.email,
            "receiverEmail" to receiverEmail,
            "date" to Timestamp.now(),
            "content" to content ,
            "fromPostId" to fromPostId,
        )).addOnSuccessListener {
            Log.v("로그", "업로드 완료")
            if (onSuccess != null) onSuccess()
        }.addOnFailureListener {
            Log.v("로그", "업로드 실패")
            if (onFailure != null) onFailure()
        }
    }
}