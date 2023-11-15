package com.hansung.androidusedtradeproject.fragment

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hansung.androidusedtradeproject.R
import com.hansung.androidusedtradeproject.Service.SalesPostService
import com.hansung.androidusedtradeproject.SignActivity
import com.hansung.androidusedtradeproject.model.SalesPost

class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // 두 번째 프래그먼트의 레이아웃을 정의하고 반환
        var root = inflater.inflate(R.layout.main_fragment_register, container, false)

        root.findViewById<TextView>(R.id.userEmail).text = SalesPostService.instance.email

        // 가격 입력 창에서 enter 입력 시 등록 실행
        root.findViewById<EditText>(R.id.price).setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.action == KeyEvent.ACTION_DOWN &&
                        event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                // Enter 키가 눌렸을 때 실행할 함수 호출
                register(root)

                // enter 입력을 문자열에 추가하지 않음
                return@setOnEditorActionListener true
            }
            false
        }

        // 올리기 버튼 클릭 시 등록 실행
        root.findViewById<Button>(R.id.addPostButton).setOnClickListener {
            register(root)
        }

        return root;
    }

    /**
     * 등록 유효성 검사
     */
    private fun validateRegister(title: String, content: String, price: String): Boolean {
        if (title.isNullOrEmpty()) {
            Toast.makeText(activity, "제목을 입력해주세요",
                Toast.LENGTH_SHORT).show()
            return false
        }

        if (content.isNullOrEmpty()) {
            Toast.makeText(activity, "내용을 입력해주세요",
                Toast.LENGTH_SHORT).show()
            return false
        }

        if (price.isNullOrEmpty()) {
            Toast.makeText(activity, "가격을 입력해주세요",
                Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    /**
     * 등록 실행 함수
     */
    private fun register(root: View) {
        val title = root.findViewById<EditText>(R.id.titleText).text.toString()
        val content = root.findViewById<EditText>(R.id.contentText).text.toString()
        val price = root.findViewById<EditText>(R.id.price).text.toString()

        if (validateRegister(title, content, price)) {
            SalesPostService.instance.addPost(title, content, price.toInt())
            Toast.makeText(activity, "등록이 완료되었습니다.",
                Toast.LENGTH_SHORT).show()

            root.findViewById<EditText>(R.id.titleText).text.clear()
            root.findViewById<EditText>(R.id.contentText).text.clear()
            root.findViewById<EditText>(R.id.price).text.clear()
        }
    }
}
