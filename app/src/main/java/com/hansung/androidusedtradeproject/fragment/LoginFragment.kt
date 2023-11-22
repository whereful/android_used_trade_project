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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hansung.androidusedtradeproject.MainActivity
import com.hansung.androidusedtradeproject.R

class LoginFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 로그인 프레그먼트 생성
        root = inflater.inflate(R.layout.sign_fragment_login, container, false)

        root.findViewById<Button>(R.id.login)?.setOnClickListener {
            val userEmail = root.findViewById<EditText>(R.id.userEmail)?.text.toString()
            val password = root.findViewById<EditText>(R.id.password)?.text.toString()
            doLogin(userEmail, password)
        }

        /**
         * 엔터 입력 시 실행
         */
        root.findViewById<EditText>(R.id.password).setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.action == KeyEvent.ACTION_DOWN &&
                        event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                // Enter 키가 눌렸을 때 실행할 함수 호출
                val userEmail = root.findViewById<EditText>(R.id.userEmail)?.text.toString()
                val password = root.findViewById<EditText>(R.id.password)?.text.toString()
                doLogin(userEmail, password)

                // enter 입력을 문자열에 추가하지 않음
                return@setOnEditorActionListener true
            }
            false
        }


        // 위에서 정의한 inflater을 반환해야 함
        return root;
    }

    /**
     * 로그인 관련 절차
     */
    private fun doLogin(userEmail: String, password: String) {
        if (!validateEmailAndPassword(userEmail, password)) {
            return
        }

        Firebase.auth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener() {
                if (it.isSuccessful) {
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.putExtra("email", userEmail)
                    startActivity(intent)
                } else if (!it.exception?.message.isNullOrEmpty()) {
                    // 로그인 할 시 오류가 발생 : 존재하지 않는 계정이거나 비밀번호 일치하지 않음
                    Toast.makeText(
                        activity, "존재하지 않는 계정이거나 비밀번호가 일치하지 않습니다.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    /**
     * 이메일, 패스워드가 null이거나 빈 문자열인 경우
     */
    private fun checkEmailAndPasswordNullOrEmpty(userEmail: String, password: String): Boolean {
        if (userEmail.isNullOrEmpty()) {
            Toast.makeText(activity, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false;
        }

        if (password.isNullOrEmpty()) {
            Toast.makeText(activity, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false;
        }

        return true;
    }

    /**
     * 이메일이 형식에 맞지 않은 경우
     */
    private fun checkEmailFormat(userEmail: String): Boolean {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Toast.makeText(activity, "이메일 형식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false;
        }
        return true;
    }

    /**
     * 패스워드가 6자리 미만인 경우
     */
    private fun checkPasswordFormat(password: String): Boolean {
        if (password.length < 6) {
            Toast.makeText(
                activity, "비밀번호는 6자리 이상 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show()
            return false;
        }
        return true;
    }

    /**
     * 이메일, 패스워드 유효성 검사
     */
    private fun validateEmailAndPassword(userEmail: String, password: String): Boolean {
        if (!checkEmailAndPasswordNullOrEmpty(userEmail, password)) {
            return false
        }

        if (!checkEmailFormat(userEmail)) {
            return false
        }

        if (!checkPasswordFormat(password)) {
            return false
        }

        return true
    }
}
