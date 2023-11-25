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
import com.hansung.androidusedtradeproject.Service.UserService
import com.hansung.androidusedtradeproject.model.AppUser
import java.text.SimpleDateFormat

class SignUpFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        root = inflater.inflate(R.layout.sign_fragment_signup, container, false)

        root.findViewById<Button>(R.id.sign_up)?.setOnClickListener {
            val userName = root.findViewById<EditText>(R.id.userName)?.text.toString()
            val userBirth = root.findViewById<EditText>(R.id.userBirth)?.text.toString()
            val userEmail = root.findViewById<EditText>(R.id.senderEmail)?.text.toString()
            val password = root.findViewById<EditText>(R.id.password)?.text.toString()
            doSignUp(userName, userBirth, userEmail, password)
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
                val userName = root.findViewById<EditText>(R.id.userName)?.text.toString()
                val userBirth = root.findViewById<EditText>(R.id.userBirth)?.text.toString()
                val userEmail = root.findViewById<EditText>(R.id.senderEmail)?.text.toString()
                val password = root.findViewById<EditText>(R.id.password)?.text.toString()
                doSignUp(userName, userBirth, userEmail, password)

                // enter 입력을 문자열에 추가하지 않음
                return@setOnEditorActionListener true
            }
            false
        }


        // 위에서 정의한 inflater을 반환해야 함
        return root;
    }

    /**
     * 회원가입 관련 절차
     * 회원가입 완료 시 정보 추가하기
     */
    private fun doSignUp(userName: String, userBirth: String, userEmail: String, password: String) {
        if (!validateEmailAndPassword(userName, userBirth, userEmail, password)) {
            return;
        }

        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener() {
                if (it.isSuccessful) {

                    UserService.setUserData(
                        AppUser(
                            name = userName,
                            birth = userBirth,
                            email = userEmail,
                            uid = it.result.user!!.uid
                        )
                    )

                    // 여기에 변수를 생성해야 함
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // 회원 가입 시 오류 : 이미 존재하는 계정
                    Toast.makeText(
                        activity, "이미 존재하는 계정입니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    /**
     * 이름, 생년월일, 이메일, 패스워드가 null이거나 빈 문자열인 경우
     */
    private fun checkNameAndBirthAndEmailAndPasswordNullOrEmpty(
        userName: String,
        userBirth: String,
        userEmail: String,
        password: String
    ): Boolean {
        if (userName.isNullOrEmpty()) {
            Toast.makeText(activity, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (userBirth.isNullOrEmpty()) {
            Toast.makeText(activity, "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (userEmail.isNullOrEmpty()) {
            Toast.makeText(activity, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isNullOrEmpty()) {
            Toast.makeText(activity, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    /**
     * 이름이 1자리 미만인 경우
     */
    private fun checkUserNameFormat(userName: String): Boolean {
        if (userName.isEmpty()) {
            Toast.makeText(
                activity, "이름은 1자리 이상 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show()
            return false;
        }
        return true;
    }

    /**
     * 날짜 형식에 맞지 않은 경우
     */
    private fun checkUserBirthFormat(userBirth: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        dateFormat.isLenient = false

        try {
            // 입력된 문자열을 Date 객체로 파싱하려 시도
            val date = dateFormat.parse(userBirth)

            if (date == null) {
                Toast.makeText(activity, "날짜 형식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        } catch (e: Exception) {
            // 예외가 발생하면 유효하지 않은 날짜 형식
            Toast.makeText(activity, "날짜 형식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    /**
     * 이메일이 형식에 맞지 않은 경우
     */
    private fun checkEmailFormat(userEmail: String): Boolean {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Toast.makeText(activity, "이메일 형식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
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
            return false
        }
        return true
    }

    /**
     * 이메일, 패스워드 유효성 검사
     */
    private fun validateEmailAndPassword(
        userName: String, userBirth: String,
        userEmail: String, password: String
    ): Boolean {
        if (!checkNameAndBirthAndEmailAndPasswordNullOrEmpty(
                userName, userBirth,
                userEmail, password
            )
        ) {
            return false
        }

        if (!checkUserNameFormat(userName)) {
            return false
        }

        if (!checkUserBirthFormat(userBirth)) {
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
