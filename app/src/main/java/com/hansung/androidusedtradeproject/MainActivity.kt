package com.hansung.androidusedtradeproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
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

        // intent로 전달받은 문자열 저장
        val email = intent.getStringExtra("email")

        // Bundle 객체에 문자열, 회원 정보 저장
        val bundle = Bundle()
        bundle.putString("email", email)
        // bundle.putParcelable("CURRENT_USER", FirebaseAuth.getInstance().currentUser)
        accountFragment.arguments = bundle

        if (Firebase.auth.currentUser == null) {
            startActivity(
                Intent(this, SignActivity::class.java)
            )
            finish()
        }

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
}