package com.hansung.androidusedtradeproject

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hansung.androidusedtradeproject.Activity.SalePostUploadActivity
import com.hansung.androidusedtradeproject.Service.SalesPostService

import com.hansung.androidusedtradeproject.fragment.AccountFragment
import com.hansung.androidusedtradeproject.fragment.ListFragment
import com.hansung.androidusedtradeproject.fragment.MessageFragment
import com.hansung.androidusedtradeproject.fragment.RegisterFragment


class MainActivity : AppCompatActivity() {

    private val listFragment = ListFragment()
    private val registerFragment = RegisterFragment()
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

        initSalesPostService(Firebase.auth.currentUser!!.uid, intent.getStringExtra("email"))

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
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragmentContainer, registerFragment)
                .commit()
            //var intent = Intent(this, SalePostUploadActivity::class.java)
            //startActivity(intent)
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
        Firebase.auth.signOut()
    }

    /**
     * 뒤로 가기 버튼 막기
     */
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // super.onBackPressed()
    }

    /**
     * SalesPostSerivce에 uid, email 값 전달
     */
    private fun initSalesPostService(uid: String?, email: String?) {
        validateUidAndEmail(uid, email)

        SalesPostService.instance.uid = uid.orEmpty()
        SalesPostService.instance.email = email.orEmpty()
    }

    /**
     * uid, email이 null이거나 ""인지 확인
     */
    private fun validateUidAndEmail(uid: String?, email: String?) {
        if (uid.isNullOrEmpty() or email.isNullOrEmpty()) {
            Toast.makeText(this, "로그인 중 오류가 발생했습니다",
                Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SignActivity::class.java))
        }
    }
}