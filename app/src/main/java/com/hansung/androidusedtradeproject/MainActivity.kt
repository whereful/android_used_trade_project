package com.hansung.androidusedtradeproject

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.hansung.androidusedtradeproject.fragment.AccountFragment
import com.hansung.androidusedtradeproject.fragment.ListFragment
import com.hansung.androidusedtradeproject.fragment.MessageFragment


class MainActivity : AppCompatActivity() {

    private val listFragment = ListFragment()
    private val messageFragment = MessageFragment()
    private val accountFragment = AccountFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            .replace(R.id.mainFragmentContainer, listFragment)
            .commit()

        // 첫 번째 버튼 클릭 시 호출
        findViewById<Button>(R.id.btnLoginFragment).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragmentContainer, listFragment)
                .commit()
        }

        // 두 번째 버튼 클릭 시 호출
        findViewById<Button>(R.id.btnJoinFragment).setOnClickListener {
            var intent = Intent(this, SalePostUploadActivity::class.java)
            startActivity(intent)
        }

        // 세 번째 버튼 클릭 시 호출
        findViewById<Button>(R.id.btnShowMessageFragment).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragmentContainer, messageFragment)
                .commit()
        }

        // 네 번째 버튼 클릭 시 호출
        findViewById<Button>(R.id.btnShowAccountFragment).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragmentContainer, accountFragment)
                .commit()
        }

        // 앱이 종료될 때 자동 로그아웃
        //Firebase.auth.signOut()
    }

    /**
     * 뒤로 가기 버튼 막기
     */
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // super.onBackPressed()
    }
}