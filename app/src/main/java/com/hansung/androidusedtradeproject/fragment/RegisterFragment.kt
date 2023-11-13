package com.hansung.androidusedtradeproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hansung.androidusedtradeproject.R
import com.hansung.androidusedtradeproject.Service.SalesPostService
import com.hansung.androidusedtradeproject.SignActivity
import com.hansung.androidusedtradeproject.model.SalesPost

class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//        val currentUser = arguments?.getParcelable<FirebaseUser>("CURRENT_USER")
//        println(currentUser)

        // 두 번째 프래그먼트의 레이아웃을 정의하고 반환
        var root = inflater.inflate(R.layout.main_fragment_register, container, false)

        root.findViewById<Button>(R.id.addPostButton).setOnClickListener {
            val title = root.findViewById<EditText>(R.id.titleText).text.toString()
            val content = root.findViewById<EditText>(R.id.contentText).text.toString()
            val price = root.findViewById<EditText>(R.id.price).text.toString().toInt()


            println(SalesPostService.instance.addPost(title, content, price))
        }

        return root;
    }
}
