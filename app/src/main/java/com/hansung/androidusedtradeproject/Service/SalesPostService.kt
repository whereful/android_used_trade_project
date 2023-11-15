package com.hansung.androidusedtradeproject.Service
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * Firebase.auth.currentUser이 null로 설정되어 등록되지 않고 강제 종료되는 오류 발생
 * Firebase.auth.currentUser 대신 객체에 속성을 정의
 *
 * 객체에 uid, email 속성 설정, get, set 함수 설정
 * writer 속성을 uid 속성으로 변경
 *
 * kotlin은 getter, setter이 미리 정의되어 있음
 *
 * ;는 사용하지 않는 것이 바람직함
 *
 */
class SalesPostService {

    // 싱글톤 객체 설정
    companion object {
        val instance = SalesPostService()
    }

    val db: FirebaseFirestore = Firebase.firestore
    val itemsCollectionRef = db.collection("posts")
    var uid = ""
        set(userUid) {
            field = userUid;
        }

    var email = ""
        set(userEmail) {
            field = userEmail
        }


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
        if(SalesPostService.instance.email.isNullOrEmpty()){
            Log.v("로그", "인증 실패")
            return
        }

        itemsCollectionRef.add(
            hashMapOf(
                "title" to title,
                "content" to content,
                "price" to price,
                "soldOut" to false,
                "uid" to uid,
                "email" to email
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