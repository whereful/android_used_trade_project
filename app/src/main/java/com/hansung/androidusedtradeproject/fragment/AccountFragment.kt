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
import com.hansung.androidusedtradeproject.SignActivity

class AccountFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // MainActivity에서 매개변수로 작성한 key값과 일치해야 함
        val str = arguments?.getString("email");

        // 네 번째 프래그먼트의 레이아웃을 정의하고 반환
        root = inflater.inflate(R.layout.main_fragment_account, container, false)

        root.findViewById<TextView>(R.id.textUID).text = "account : $str"

        root.findViewById<Button>(R.id.sign_out)?.setOnClickListener {

            Firebase.auth.signOut()

            startActivity(
                Intent(activity, SignActivity::class.java)
            )
            activity?.finish()
        }


        // 위에서 정의한 inflater을 반환해야 함
        return root;
    }
}
