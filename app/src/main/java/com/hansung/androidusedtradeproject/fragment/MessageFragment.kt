package com.hansung.androidusedtradeproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hansung.androidusedtradeproject.R
import com.hansung.androidusedtradeproject.Service.MessageService
import com.hansung.androidusedtradeproject.model.AppMessage

class MessageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 세 번째 프래그먼트의 레이아웃을 정의하고 반환
        val root = inflater.inflate(R.layout.main_fragment_message, container, false)


        val task = MessageService.getUsersMessage()
        task?.addOnSuccessListener {
            var messageString = "";
            val messages = AppMessage.makeListByQuerySnapshot(it)
            messages.forEach { msg ->
                messageString += msg.print()
            }
            root.findViewById<TextView>(R.id.messageListText).text = messageString
        }


        return root;
    }
}
