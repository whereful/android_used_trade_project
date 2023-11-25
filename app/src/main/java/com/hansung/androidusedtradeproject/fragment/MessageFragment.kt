package com.hansung.androidusedtradeproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hansung.androidusedtradeproject.adapter.MessageAdapter
import com.hansung.androidusedtradeproject.R
import com.hansung.androidusedtradeproject.Service.MessageService
import com.hansung.androidusedtradeproject.model.AppMessage

class MessageFragment : Fragment() {

    private var messageList = mutableListOf<AppMessage>()
    private var messageAdapter: MessageAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 세 번째 프래그먼트의 레이아웃을 정의하고 반환
        val root = inflater.inflate(R.layout.main_fragment_message, container, false)


        val task = MessageService.getUsersMessage()
        task?.addOnSuccessListener {
            // makeListByQuerySnapshot를 통해서 스냅샷으로 글 목록 리스트로 변환 할 수 있습니다.
            messageList = AppMessage.makeListByQuerySnapshot(it)

            val recyclerView = root.findViewById<RecyclerView>(R.id.message_recyclerview)
            recyclerView.layoutManager = LinearLayoutManager(activity)

            messageAdapter = MessageAdapter(messageList)
            recyclerView.adapter = messageAdapter
        }


        return root;
    }
}
