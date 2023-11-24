package com.hansung.androidusedtradeproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hansung.androidusedtradeproject.model.SalesPost

class MyAdapter(
    var items: MutableList<SalesPost>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    /**
     * 원본 저장
     */
    var original = items.filter { true }

    /**
     * 클릭 이벤트 인터페이스
     */
    interface OnItemClickListener {
        fun onItemClick(item: SalesPost)
    }

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
        holder.soldOut.text = "판매됨 : " + items[position].soldOut.toString()

        // 클릭 이벤트 등록
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(items[position])
        }
    }

    fun setData(data: MutableList<SalesPost>) {
        items = data
        original = items.filter { true }
    }

    fun filter(c1: Boolean, c2: Boolean) {

        if (c1 and c2) {
            items = original.filter { true }.toMutableList()
            notifyDataSetChanged()
        } else if (!c1 and !c2) {
            items = original.filter { false }.toMutableList()
            notifyDataSetChanged()
        } else if (c1 and !c2) {
            items = original.filter { it.soldOut == true }.toMutableList()
            notifyDataSetChanged()
        } else if (!c1 and c2) {
            items = original.filter { it.soldOut == false }.toMutableList()
            notifyDataSetChanged()
        }
    }
}