package com.hansung.androidusedtradeproject.model

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.io.Serializable

/**
 * Firebase.auth.currentUser이 null로 설정되는 오류 발생
 * 객체에 uid, email 속성 설정, get, set 함수 설정
 * writer 속성을 uid 속성으로 변경
 */
data class SalesPost(
    var id : String,
    var title : String ,
    var content : String,
    var price : Int,
    var soldOut : Boolean,
    var uid: String,
    var email: String
    )  : Serializable{

    constructor(doc: QueryDocumentSnapshot) :
            this(
                doc.id,
                doc["title"].toString(),
                doc["content"].toString(),
                doc["price"].toString().toIntOrNull() ?: 0,
                doc["soldOut"].toString().toBoolean(),
                doc["uid"].toString(),
                doc["email"].toString()
            )

    fun print() : String{
        return  "id : $id\n" +
                "title : $title\n" +
                "content : $content\n" +
                "price : $price\n" +
                "soldOut : $soldOut\n" +
                "uid : $uid\n" +
                "email : $email"
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
