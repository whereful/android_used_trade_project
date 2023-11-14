package com.hansung.androidusedtradeproject.Activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.hansung.androidusedtradeproject.R
import com.hansung.androidusedtradeproject.model.SalesPost

class SalesPostDetailActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_post_detail)

        val post = intent.getSerializableExtra("post" , SalesPost::class.java)

        if(post == null){
            Toast.makeText(this, "판매글 정보가 없습니다.", Toast.LENGTH_SHORT).show()
        }else{
            Log.v("로그" , post.print())
        }


        findViewById<TextView>(R.id.titleText).text = post?.title
        findViewById<TextView>(R.id.contentText).text = post?.content
        findViewById<TextView>(R.id.price_text).text = post?.price.toString() + "원"
        findViewById<TextView>(R.id.writer_text).text = post?.writer
    }
}