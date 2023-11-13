package com.hansung.androidusedtradeproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hansung.androidusedtradeproject.R
import com.hansung.androidusedtradeproject.Service.SalesPostService
import com.hansung.androidusedtradeproject.model.SalesPost

class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 첫 번째 프래그먼트의 레이아웃을 정의하고 반환
        val root = inflater.inflate(R.layout.main_fragment_list, container, false)

//        root.findViewById<Button>(R.id.loadButton).setOnClickListener {
//            SalesPostService().getPostsBySoldOut(true).addOnSuccessListener {
//
//                //#. makeListByQuerySnapshot를 통해서 스냅샷으로 글 목록 리스트로 변환 할 수 있습니다.
//                val postList = SalesPost.makeListByQuerySnapshot(it);
//
//
//                Toast.makeText(activity, "불러오기 성공", Toast.LENGTH_SHORT).show()
//            }
//        }

        return root;
    }
}
