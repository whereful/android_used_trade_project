package com.hansung.androidusedtradeproject.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hansung.androidusedtradeproject.MyAdapter
import com.hansung.androidusedtradeproject.R
import com.hansung.androidusedtradeproject.SalePostUploadActivity
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

    var postList = mutableListOf<SalesPost>()
    var adapter: MyAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 첫 번째 프래그먼트의 레이아웃을 정의하고 반환
        val root = inflater.inflate(R.layout.main_fragment_list, container, false)

        drawLinearLayoutBackgroundAndBorder(root)
        drawTextViewBorder(root)


        SalesPostService.instance.getPosts().addOnSuccessListener {
            // makeListByQuerySnapshot를 통해서 스냅샷으로 글 목록 리스트로 변환 할 수 있습니다.
            postList = SalesPost.makeListByQuerySnapshot(it)

            val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview)
            recyclerView.layoutManager = LinearLayoutManager(activity)

            adapter = MyAdapter(postList)
            recyclerView.adapter = adapter

        }

        root.findViewById<Button>(R.id.button_register).setOnClickListener {
            val intent = Intent(activity, SalePostUploadActivity::class.java)

            startActivity(intent)
        }

        root.findViewById<Button>(R.id.button_filter).setOnClickListener {
            showCheckBoxDialog(adapter)
        }

        return root;
    }


    // 버튼 클릭 시 다이얼로그 표시
    private fun showCheckBoxDialog(adapter: MyAdapter?) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Checkbox Dialog")

        // 다이얼로그 내에 CheckBox 추가
        val linearLayout = LinearLayout(requireContext())
        linearLayout.orientation = LinearLayout.VERTICAL

        val checkBox1 = CheckBox(requireContext())
        checkBox1.text = "판매 중"
        linearLayout.addView(checkBox1)

        val checkBox2 = CheckBox(requireContext())
        checkBox2.text = "판매 완료"
        linearLayout.addView(checkBox2)

        builder.setView(linearLayout)

        // 확인 버튼 설정 및 클릭 리스너 추가
        builder.setPositiveButton("OK") { _, _ ->
            // CheckBox 상태 확인
            val isChecked1 = checkBox1.isChecked
            val isChecked2 = checkBox2.isChecked

            adapter?.filter(isChecked1, isChecked2)
        }

        // 취소 버튼 설정
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        // 다이얼로그 표시
        builder.show()
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

    fun refreshAdapter() {
//        println("호출")
        SalesPostService.instance.getPosts().addOnSuccessListener {
            // makeListByQuerySnapshot를 통해서 스냅샷으로 글 목록 리스트로 변환 할 수 있습니다.
            postList = SalesPost.makeListByQuerySnapshot(it)

//            println(postList)
            adapter?.setData(postList)
            adapter?.notifyDataSetChanged()
        }
    }
}
