package com.hansung.androidusedtradeproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.hansung.androidusedtradeproject.Service.SalesPostService
import com.hansung.androidusedtradeproject.model.SalesPost

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        findViewById<Button>(R.id.post_detail_view_button).setOnClickListener{
            var intent = Intent(this, SalesPostDetailActivity::class.java)

            SalesPostService().getPostById("xqCXLMQ1QQgQ2Mu0oQpR").addOnSuccessListener {
                if(it.exists()){
                    var post = SalesPost(it);
                    intent.putExtra(
                        "post" ,post
                    )
                    Log.v("로그" , post.print())
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "글이 없음", Toast.LENGTH_SHORT).show()
                }
            }
        }

        /*findViewById<Button>(R.id.postUploadTestPageButton).setOnClickListener {
            var intent = Intent(this, SalePostUploadActivity::class.java)
            startActivity(intent)
        }*/
    }
}