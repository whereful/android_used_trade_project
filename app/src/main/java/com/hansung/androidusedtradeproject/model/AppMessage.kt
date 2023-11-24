package com.hansung.androidusedtradeproject.model

import android.icu.text.SimpleDateFormat
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

data class AppMessage(
    val senderEmail : String,
    val receiverEmail : String,
    val date : String,
    val content : String,
    val fromPostId : String,
){

    companion object {
        fun makeListByQuerySnapshot(snapshot: QuerySnapshot): MutableList<AppMessage> {
            val items = mutableListOf<AppMessage>()
            for (doc in snapshot) {
                var item = AppMessage(doc)
                items.add(item)
            }
            return items;
        }
    }

    constructor(doc: QueryDocumentSnapshot) :
            this(
                senderEmail = doc["senderEmail"].toString(),
                receiverEmail = doc["receiverEmail"].toString(),
                date = SimpleDateFormat("yyyyMMdd HH:mm").format((doc["date"] as Timestamp).toDate())
                    .toString(),
                content = doc["content"].toString(),
                fromPostId = doc["fromPostId"].toString(),
            )

    fun print(): String {
        return "senderEmail : $senderEmail\n" +
                "receiverEmail : ${receiverEmail}\n" +
                "date : $date\n" +
                "content : $content\n" +
                "fromPostId : $fromPostId\n"
    }
}
