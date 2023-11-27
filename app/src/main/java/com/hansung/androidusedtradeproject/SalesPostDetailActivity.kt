package com.hansung.androidusedtradeproject

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.hansung.androidusedtradeproject.Service.MessageService
import com.hansung.androidusedtradeproject.dialog.DialogHelper
import com.hansung.androidusedtradeproject.model.SalesPost

class SalesPostDetailActivity : AppCompatActivity(), DialogHelper.InputTextDialogListener {

    private val dialogHelper = DialogHelper(this)
    private var post: SalesPost? = null

    companion object {

        /**
         * 대상 SalePost를 표시하는 글 자세히보기 페이지 액티비티 실행
         *
         * @param activity 현재페이지
         * @param post 대상이되는 SalePost
         */
        fun startWithPost(activity: Activity, post: SalesPost) {
            var intent = Intent(activity, SalesPostDetailActivity::class.java)
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
        setContentView(R.layout.activity_sales_post_detail)

        post = intent.getSerializableExtra("post", SalesPost::class.java)

        if (post == null) {
            Toast.makeText(this, "판매글 정보가 없습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Log.v("로그", post!!.print())
        }

        findViewById<TextView>(R.id.titleText).text = "제목 : ${post?.title}"
        findViewById<TextView>(R.id.contentText).text = "내용 : ${post?.content}"
        findViewById<TextView>(R.id.price_text).text = "가격 : ${post?.price.toString()}원"
        findViewById<TextView>(R.id.writer_text).text = "이메일 : ${post?.email}"
        findViewById<TextView>(R.id.dateText).text = "날짜 : ${post?.date.toString()}"
        findViewById<TextView>(R.id.soldOut_text).text = "판매됨 : ${post?.soldOut.toString()}"

        findViewById<Button>(R.id.chat_button).setOnClickListener {
            onShowInputDialogButtonClick()
            //startActivity(Intent(this , SendMessageActivity::class.java))
        }
    }

    /**
     * 사용자가 입력한 텍스트를 이용한 작업 수행
     */
    /**
     * fromPostTitle 추가
     */
    override fun onInputText(text: String) {
        if (post != null && validateMessage(text)) {
            MessageService.sendMessage(
                receiverEmail = post!!.email,
                content = text,
                fromPostId = post!!.id,
                fromPostTitle = post!!.title,
                onSuccess = {
                    Toast.makeText(this, "메세지 보내기 성공", Toast.LENGTH_SHORT).show()
                },
                onFailure = {
                    Toast.makeText(this, "메세지 보내기 실패", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    /**
     * 다이얼로그 표시 버튼 클릭 시 호출될 메서드
     */
    private fun onShowInputDialogButtonClick() {
        dialogHelper.showInputDialog("메시지를 입력하세요", this)
    }

    /**
     * 메시지 유효성 검사
     */
    private fun validateMessage(message: String): Boolean {
        if (message.isNullOrBlank()) {
            Toast.makeText(
                this, "메시지를 입력해주세요",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }
}