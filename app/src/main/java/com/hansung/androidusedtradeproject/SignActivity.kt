package com.hansung.androidusedtradeproject

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hansung.androidusedtradeproject.fragment.LoginFragment
import com.hansung.androidusedtradeproject.fragment.SignUpFragment

class SignActivity : AppCompatActivity() {

    /**
     * 시간 저장 변수
     */
    private var backPressedTime: Long = 0

    private val loginFragment = LoginFragment()
    private val signUpFragment = SignUpFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        // LinearLayout 찾기 (ID에 따라 수정)
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)

        // 테두리를 그리기 위한 GradientDrawable 생성
        val border = GradientDrawable()
        border.setColor(Color.GRAY) // 배경색
        border.setStroke(2, Color.BLACK) // 테두리 두께 및 색상

        // 만든 GradientDrawable을 LinearLayout의 배경으로 설정
        linearLayout.background = border

        // 처음에는 첫 번째 프래그먼트를 표시
        supportFragmentManager.beginTransaction()
            .replace(R.id.signFragmentContainer, loginFragment)
            .commit()

        // 첫 번째 버튼 클릭 시 호출
        findViewById<Button>(R.id.btnLoginFragment).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.signFragmentContainer, loginFragment)
                .commit()
        }

        // 두 번째 버튼 클릭 시 호출
        findViewById<Button>(R.id.btnJoinFragment).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.signFragmentContainer, signUpFragment)
                .commit()
        }

    }

    /**
     * 뒤로 가기 버튼 막기
     */
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // super.onBackPressed()
        if (System.currentTimeMillis() - backPressedTime >= 2000) {
            backPressedTime = System.currentTimeMillis()
            Toast.makeText(
                this, "뒤로 가기 버튼을 한번 더 누르면 종료됩니다.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            finish()
        }
    }

}