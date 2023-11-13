package com.hansung.androidusedtradeproject

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        findViewById<Button>(R.id.sign_in)?.setOnClickListener {
            // 전역으로 변수 생성하면 오류 발생
            val userEmail = findViewById<EditText>(R.id.userEmail)?.text.toString()
            val password = findViewById<EditText>(R.id.password)?.text.toString()
            doLogin(userEmail, password)

        }

        findViewById<Button>(R.id.sign_up)?.setOnClickListener {
            val userEmail = findViewById<EditText>(R.id.userEmail)?.text.toString()
            val password = findViewById<EditText>(R.id.password)?.text.toString()
            doSignUp(userEmail, password)
        }

        findViewById<EditText>(R.id.password).setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.action == KeyEvent.ACTION_DOWN &&
                        event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                // Enter 키가 눌렸을 때 실행할 함수 호출
                val userEmail = findViewById<EditText>(R.id.userEmail)?.text.toString()
                val password = findViewById<EditText>(R.id.password)?.text.toString()
                doLogin(userEmail, password)

                // enter 입력을 문자열에 추가하지 않음
                return@setOnEditorActionListener true
            }
            false
        }

    }

    // 로그인 관련 절차
    private fun doLogin(userEmail: String, password: String) {
        if (!validateEmailAndPassword(userEmail, password)) {
            return;
        }

        Firebase.auth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("email", userEmail)

                    startActivity(intent)
                    // finish()

                } else if (!it.exception?.message.isNullOrEmpty()){
                    // 로그인 할 시 오류가 발생 : 존재하지 않는 계정이거나 비밀번호 일치하지 않음
                    Toast.makeText(this, "존재하지 않는 계정이거나 비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show()
                }

            }

    }

    // 회원가입 관련 절차
    private fun doSignUp(userEmail: String, password: String) {
        if (!validateEmailAndPassword(userEmail, password)) {
            return;
        }

        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    // 여기에 변수를 생성해야 함
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("email", userEmail)

                    startActivity(intent)
                    finish()
                } else {
                    // 회원 가입 시 오류 : 이미 존재하는 계정
                    Toast.makeText(this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }



    // 이메일, 패스워드가 null이거나 빈 문자열인 경우
    private fun checkEmailAndPasswordNullOrEmpty(userEmail: String, password: String): Boolean {
        if (userEmail.isNullOrEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false;
        }

        if (password.isNullOrEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false;
        }

        return true;
    }

    // 이메일이 형식에 맞지 않은 경우
    private fun checkEmailFormat(userEmail: String): Boolean {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Toast.makeText(this, "이메일 형식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false;
        }
        return true;
    }

    // 패스워드가 6자리 미만인 경우
    private fun checkPasswordFormat(password: String): Boolean {
        if (password.length < 6) {
            Toast.makeText(this, "비밀번호는 6자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false;
        }
        return true;
    }

    // 이메일, 패스워드 유효성 검사
    private fun validateEmailAndPassword(userEmail: String, password: String): Boolean {
        if (!checkEmailAndPasswordNullOrEmpty(userEmail, password)) {
            return false;
        }

        if (!checkEmailFormat(userEmail) || !checkPasswordFormat(password)) {
            return false;
        }
        return true;
    }

}