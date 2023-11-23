package com.hansung.androidusedtradeproject

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.hansung.androidusedtradeproject.Service.SalesPostService
import com.hansung.androidusedtradeproject.model.SalesPost

class SalePostModifyActivity : AppCompatActivity() {

    companion object{

        /**
         * 대상 SalePost를 표시하는 글 수정하기 페이지 액티비티 실행
         *
         * @param activity 현재페이지
         * @param post 대상이되는 SalePost
         */
        fun startWithPost(activity: Activity, post : SalesPost){
            var intent = Intent(activity, SalePostModifyActivity::class.java)
            intent.putExtra(
                "post", post
            )
            Log.v("로그", post.print())
            activity.startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_post_modify)

        val post = intent.getSerializableExtra("post", SalesPost::class.java)

        if (post == null) {
            Toast.makeText(this, "판매글 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        else{
            val title = findViewById<EditText>(R.id.titleEditText)
            val content = findViewById<EditText>(R.id.contentEditText)
            val price = findViewById<EditText>(R.id.priceEditText)
            var soldOutSwitch = findViewById<Switch>(R.id.soldOutSwitch)

            title.setText(post.title)
            content.setText(post.content)
            price.setText(post.price.toString())
            soldOutSwitch.isChecked = post.soldOut

            findViewById<Button>(R.id.modifyButton).setOnClickListener {
                post.title = title.text.toString()
                post.content = content.text.toString()
                post.price = price.text.toString().toInt()
                post.soldOut = soldOutSwitch.isChecked

                SalesPostService.instance.modifyPost(
                    post,
                    onSuccess = {
                        Toast.makeText(this, "글 수정 성공", Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    onFailure = {
                        Toast.makeText(this, "글 수정 실패", Toast.LENGTH_SHORT).show()
                    },
                    onAuthFail = {
                        Toast.makeText(this, "글 수정 실패 : 로그인이 되어 있지 않음", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}