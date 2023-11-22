package com.hansung.androidusedtradeproject.model

import android.icu.text.SimpleDateFormat
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.io.Serializable


data class SalesPost(
    var id : String,
    var date : String,
    var title : String ,
    var email : String,
    var content : String,
    var price : Int,
    var soldOut : Boolean
)  : Serializable{

    constructor(doc: QueryDocumentSnapshot) :
            this(
                id = doc.id,
                date = SimpleDateFormat("yyyyMMdd HH:mm").format((doc["date"] as Timestamp).toDate()).toString(),
                title = doc["title"].toString(),
                email = doc["email"].toString(),
                content = doc["content"].toString(),
                price = doc["price"].toString().toIntOrNull() ?: 0,
                soldOut = doc["soldOut"].toString().toBoolean()
            )

    constructor(doc: DocumentSnapshot) :
            this(
                id = doc.id,
                date = SimpleDateFormat("yyyyMMdd HH:mm").format((doc["date"] as Timestamp).toDate()).toString(),
                title = doc["title"].toString(),
                email = doc["writerEmail"].toString(),
                content = doc["content"].toString(),
                price = doc["price"].toString().toIntOrNull() ?: 0,
                soldOut = doc["soldOut"].toString().toBoolean()
            )

    fun print() : String{
        return  "id : $id\n" +
                "date : ${date}\n" +
                "title : $title\n" +
                "writerUid : $email\n" +
                "content : $content\n" +
                "price : $price\n" +
                "soldOut : $soldOut"
    }

    companion object {
        fun makeListByQuerySnapshot(snapshot : QuerySnapshot) : MutableList<SalesPost>{
            val items = mutableListOf<SalesPost>()
            for (doc in snapshot) {
                var item = SalesPost(doc)
                items.add(item)
            }
            return items;
        }
    }
}
