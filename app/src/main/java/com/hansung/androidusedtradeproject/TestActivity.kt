package com.hansung.androidusedtradeproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.hansung.androidusedtradeproject.Service.SalesPostService
import com.hansung.androidusedtradeproject.model.SalesPost

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        findViewById<Button>(R.id.post_detail_view_button).setOnClickListener{
            var intent = Intent(this, SalesPostDetailActivity::class.java)

            intent.putExtra(
                "post" ,
                SalesPost("id" ,
                    "테스트 제목",
                    "테스트 내용" , 10000 ,
                    false, SalesPostService.instance.uid,
                    SalesPostService.instance.email)
            )
            startActivity(intent)
        }
    }
}