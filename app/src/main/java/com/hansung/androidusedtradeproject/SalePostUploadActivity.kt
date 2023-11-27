package com.hansung.androidusedtradeproject

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hansung.androidusedtradeproject.Service.SalesPostService

class SalePostUploadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_post_upload)

        findViewById<Button>(R.id.uploadButton).setOnClickListener {
            val title = findViewById<EditText>(R.id.titleEditText).text.toString()
            val content = findViewById<EditText>(R.id.contentEditText).text.toString()
            val price = findViewById<EditText>(R.id.priceEditText).text.toString()

            /**
             * 유효성 검사
             */
            if (validateRegister(title, content, price)) {
                SalesPostService().uploadPost(
                    title = title,
                    content = content,
                    price = price.toInt(),
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
            Toast.makeText(
                this, "제목을 입력해주세요",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (content.isNullOrEmpty()) {
            Toast.makeText(
                this, "내용을 입력해주세요",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (price.isNullOrEmpty()) {
            Toast.makeText(
                this, "가격을 입력해주세요",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (price.toInt() <= 0) {
            Toast.makeText(
                this, "가격이 양수여야 합니다.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }
}