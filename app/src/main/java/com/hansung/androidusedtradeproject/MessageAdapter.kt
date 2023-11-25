package com.hansung.androidusedtradeproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hansung.androidusedtradeproject.model.AppMessage
import com.hansung.androidusedtradeproject.model.SalesPost

class MessageAdapter(
    var items: MutableList<AppMessage>
) :
    RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val senderEmail = v.findViewById<TextView>(R.id.senderEmail)
        val date = v.findViewById<TextView>(R.id.date)
        val content = v.findViewById<TextView>(R.id.content)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.message_layout, parent, false)
        val viewHolder = MyViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * 레이아웃의 텍스트에 SalesPost 속성의 내용 반영
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.senderEmail.text = "보낸 이 : " + items[position].senderEmail
        holder.date.text = "날짜 : " + items[position].date
        holder.content.text = "내용 : " + items[position].content

        // 클릭 이벤트 등록

    }

}