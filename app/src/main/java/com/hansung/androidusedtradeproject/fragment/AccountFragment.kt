package com.hansung.androidusedtradeproject.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hansung.androidusedtradeproject.R
import com.hansung.androidusedtradeproject.Service.UserService
import com.hansung.androidusedtradeproject.SignActivity
import com.hansung.androidusedtradeproject.model.AppUser

class AccountFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 네 번째 프래그먼트의 레이아웃을 정의하고 반환
        root = inflater.inflate(R.layout.main_fragment_account, container, false)

        if (Firebase.auth.currentUser == null) {
            Toast.makeText(context, "로그인 되어있지 않습니다.", Toast.LENGTH_SHORT).show()
        } else {
            UserService.getUserDataByUid(Firebase.auth.currentUser!!.uid)
                .addOnSuccessListener {
                    var appUser = AppUser(it)
                    root.findViewById<TextView>(R.id.nameText).text = "name : ${appUser.name}"
                    root.findViewById<TextView>(R.id.birthText).text = "birth : ${appUser.birth}"
                    root.findViewById<TextView>(R.id.emailText).text =
                        "account : ${Firebase.auth.currentUser!!.email}"
                }.addOnFailureListener {
                    Toast.makeText(context, "유저정보 불러오기 실패", Toast.LENGTH_SHORT).show()
                }
        }

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
