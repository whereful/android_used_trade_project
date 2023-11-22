package com.hansung.androidusedtradeproject.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.TextView
import com.hansung.androidusedtradeproject.R
import com.hansung.androidusedtradeproject.Service.SalesPostService
import com.hansung.androidusedtradeproject.SignActivity
import com.hansung.androidusedtradeproject.TestActivity

class AccountFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // 네 번째 프래그먼트의 레이아웃을 정의하고 반환
        root = inflater.inflate(R.layout.main_fragment_account, container, false)

        root.findViewById<TextView>(R.id.textUID).text =
            "account : ${Firebase.auth.currentUser!!.email}"

        root.findViewById<Button>(R.id.sign_out)?.setOnClickListener {

            Firebase.auth.signOut()

            startActivity(
                Intent(activity, SignActivity::class.java)
            )
            activity?.finish()
        }

        // 테스트 이동 버튼
        root.findViewById<Button>(R.id.move_test_page_button)?.setOnClickListener {
            startActivity(
                Intent(activity, TestActivity::class.java)
            )
        }


        // 위에서 정의한 inflater을 반환해야 함
        return root;
    }
}
