package com.hansung.androidusedtradeproject.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hansung.androidusedtradeproject.MyAdapter
import com.hansung.androidusedtradeproject.R
import com.hansung.androidusedtradeproject.Service.SalesPostService
import com.hansung.androidusedtradeproject.model.SalesPost

/**
 * 작성자와 상관 없이 전체 목록을 보여주는 fragment
 *
 * SalesPost.makeListByQuerySnapshot(it)는 addOnSuccessListener 내부에서 정의되어야 오류 발생하지 않음
 *
 * 화면이 전환될 때마다 매번 갱신이 일어남
 */
class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 첫 번째 프래그먼트의 레이아웃을 정의하고 반환
        val root = inflater.inflate(R.layout.main_fragment_list, container, false)

        drawLinearLayoutBackgroundAndBorder(root)
        drawTextViewBorder(root)

        SalesPostService.instance.getPosts().addOnSuccessListener {
            // makeListByQuerySnapshot를 통해서 스냅샷으로 글 목록 리스트로 변환 할 수 있습니다.
            val postList = SalesPost.makeListByQuerySnapshot(it)

            val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = MyAdapter(postList)
        }
        return root;
    }

    private fun drawLinearLayoutBackgroundAndBorder(root: View) {
        // LinearLayout 찾기 (ID에 따라 수정)
        val linearLayout = root.findViewById<LinearLayout>(R.id.partLinearLayout)

        // 테두리를 그리기 위한 GradientDrawable 생성
        val border = GradientDrawable()
        border.setColor(Color.GREEN) // 배경색
        border.setStroke(2, Color.BLACK) // 테두리 두께 및 색상

        // 만든 GradientDrawable을 LinearLayout의 배경으로 설정
        linearLayout.background = border
    }

    private fun drawTextViewBorder(root: View) {

        val menuUser = root.findViewById<TextView>(R.id.menuUser)
        val menuTitle = root.findViewById<TextView>(R.id.menuTitle)
        val menuContent = root.findViewById<TextView>(R.id.menuContent)
        val menuPrice = root.findViewById<TextView>(R.id.menuPrice)
        val menuSoldOut = root.findViewById<TextView>(R.id.menuSoldOut)

        // 테두리를 그리기 위한 GradientDrawable 생성
        val borderUser = GradientDrawable()
        borderUser.setStroke(2, Color.BLACK) // 테두리 두께 및 색상

        val borderTitle = GradientDrawable()
        borderTitle.setStroke(2, Color.BLACK) // 테두리 두께 및 색상

        val borderContent = GradientDrawable()
        borderContent.setStroke(2, Color.BLACK) // 테두리 두께 및 색상

        val borderPrice = GradientDrawable()
        borderPrice.setStroke(2, Color.BLACK) // 테두리 두께 및 색상

        val borderSoldOut = GradientDrawable()
        borderSoldOut.setStroke(2, Color.BLACK) // 테두리 두께 및 색상

        // 만든 GradientDrawable을 TextView의 배경으로 설정
        menuUser.background = borderUser
        menuTitle.background = borderTitle
        menuContent.background = borderContent
        menuPrice.background = borderPrice
        menuSoldOut.background = borderSoldOut
    }
}
