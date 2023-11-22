package com.hansung.androidusedtradeproject.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.hansung.androidusedtradeproject.R
import com.hansung.androidusedtradeproject.Service.SalesPostService
import com.hansung.androidusedtradeproject.model.SalesPost

/*
class SalesPostTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_post_test)

        findViewById<Button>(R.id.loadButton).setOnClickListener {
            SalesPostService().getPosts().addOnSuccessListener {

                //#. makeListByQuerySnapshot를 통해서 스냅샷으로 글 목록 리스트로 변환 할 수 있습니다.
                val postList = SalesPost.makeListByQuerySnapshot(it);
                Toast.makeText(this, "불러오기 성공", Toast.LENGTH_SHORT).show()
                postList.forEach { post ->  Log.v("로그" , post.print())}
            }
        }

        findViewById<Button>(R.id.addPostButton).setOnClickListener {
            val title = findViewById<EditText>(R.id.titleText).text.toString()
            val content = findViewById<EditText>(R.id.contentText).text.toString()
            val price = findViewById<EditText>(R.id.price).text.toString().toInt()

            SalesPostService().uploadPost(title ,content,price)
        }
    }
}*/
