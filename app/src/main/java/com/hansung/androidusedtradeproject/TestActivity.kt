package com.hansung.androidusedtradeproject

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hansung.androidusedtradeproject.Service.SalesPostService
import com.hansung.androidusedtradeproject.model.SalesPost

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        //#. 글 자세히 보기 페이지로 이동
        findViewById<Button>(R.id.post_detail_view_button).setOnClickListener {

            //#. 글을 불러온후 액티비티의 startWithPost를 호출하여 해당 SalePost를 대상으로 하는 자세히보기 액티비티 실행
            SalesPostService().getPostById("2haWO73iMW9jZWCH676f").addOnSuccessListener {
                if (it.exists()) {
                    SalesPostDetailActivity.startWithPost(activity = this , post = SalesPost(it))
                } else {
                    Toast.makeText(this, "글이 없음", Toast.LENGTH_SHORT).show()
                }
            }
        }

        
        //#. 글 수정하기 페이지로 이동
        findViewById<Button>(R.id.postModifyPageButton).setOnClickListener {
            
            //#. 글을 불러온후 액티비티의 startWithPost를 호출하여 해당 SalePost를 대상으로 하는 수정하기 액티비티 실행
            SalesPostService().getPostById("2haWO73iMW9jZWCH676f").addOnSuccessListener {
                if (it.exists()) {
                    SalePostModifyActivity.startWithPost(activity = this , post = SalesPost(it))
                } else {
                    Toast.makeText(this, "글이 없음", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}