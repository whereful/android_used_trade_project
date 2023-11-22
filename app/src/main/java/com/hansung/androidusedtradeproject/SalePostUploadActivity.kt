package com.hansung.androidusedtradeproject.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hansung.androidusedtradeproject.R
import com.hansung.androidusedtradeproject.Service.SalesPostService

class SalePostUploadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_post_upload)

        findViewById<Button>(R.id.uploadButton).setOnClickListener {
            val title = findViewById<EditText>(R.id.titleEditText).text.toString()
            val price = findViewById<EditText>(R.id.priceEditText).text.toString().toInt()
            val content = findViewById<EditText>(R.id.contentEditText).text.toString()

            if(Firebase.auth.currentUser == null){
                Toast.makeText(this, "로그인 되어있지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            else{
                SalesPostService().uploadPost(
                    title = title,
                    content = content,
                    price = price,
                    onSuccess = {
                        Toast.makeText(this, "업로드 성공.", Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    onFailure = {
                        Toast.makeText(this, "업로드 실패.", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }


    /**
     * 등록 유효성 검사
     */
    private fun validateRegister(title: String, content: String, price: String): Boolean {
        if (title.isNullOrEmpty()) {
            Toast.makeText(this, "제목을 입력해주세요",
                Toast.LENGTH_SHORT).show()
            return false
        }

        if (content.isNullOrEmpty()) {
            Toast.makeText(this, "내용을 입력해주세요",
                Toast.LENGTH_SHORT).show()
            return false
        }

        if (price.isNullOrEmpty()) {
            Toast.makeText(this, "가격을 입력해주세요",
                Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}