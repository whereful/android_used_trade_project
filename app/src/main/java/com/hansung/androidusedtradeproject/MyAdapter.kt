package com.hansung.androidusedtradeproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hansung.androidusedtradeproject.model.SalesPost

class MyAdapter(val items: MutableList<SalesPost>) : RecyclerView.Adapter<MyAdapter.MyViewHolder> () {
    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val userEmail = v.findViewById<TextView>(R.id.userEmail)
        val title = v.findViewById<TextView>(R.id.title)
        val content = v.findViewById<TextView>(R.id.content)
        val price = v.findViewById<TextView>(R.id.price)
        val soldOut = v.findViewById<TextView>(R.id.soldOut)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_layout, parent, false)
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

        holder.userEmail.text = "판매자 : " + items[position].email
        holder.title.text = "제목 : " + items[position].title
        holder.content.text = "내용 : " + items[position].content
        holder.price.text = "가격 : " + items[position].price.toString()
        holder.soldOut.text = "판매 여부 : " + items[position].soldOut.toString()
    }
}