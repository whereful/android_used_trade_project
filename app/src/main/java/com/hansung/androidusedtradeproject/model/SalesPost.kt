package com.hansung.androidusedtradeproject.model

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.io.Serializable

data class SalesPost(
    var id : String,
    var title : String ,
    var writer : String,
    var content : String,
    var price : Int,
    var soldOut : Boolean
    )  : Serializable{

    constructor(doc: QueryDocumentSnapshot) :
            this(
                doc.id,
                doc["title"].toString(),
                doc["writer"].toString(),
                doc["content"].toString(),
                doc["price"].toString().toIntOrNull() ?: 0,
                doc["soldOut"].toString().toBoolean()
            )

    fun print() : String{
        return  "id : $id\n" +
                "title : $title\n" +
                "writer : $writer\n" +
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
