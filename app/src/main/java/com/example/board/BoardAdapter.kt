package com.example.board

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class BoardAdapter:RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    fun interface OnItemClickListener {
        fun onItemClick(v:View, position: Int)
    }
    private var listener:OnItemClickListener? = null

    fun setListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    //

    // List // listOf()
    private var data:List<MyBoard> = listOf()

    fun setData(data:List<MyBoard>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun swapItem(from:Int, to:Int) {
        Collections.swap(data, from, to)
        notifyItemMoved(from, to)
    }

    fun removeItem(index:Int) {
        //data.removeAt(index)
        notifyItemRemoved(index)
    }


    class BoardViewHolder(view: View, listener: OnItemClickListener?):RecyclerView.ViewHolder(view) {
        val textViewId: TextView = view.findViewById(R.id.board_idView)
        val textViewTitle: TextView = view.findViewById(R.id.titleView)
        val textViewContent: TextView = view.findViewById(R.id.contentView)
        val textViewSavedTime: TextView = view.findViewById(R.id.saved_timeView)
        val textViewBoardViews: TextView = view.findViewById(R.id.board_viewsView)

        init {
            view.setOnClickListener {
                Log.d("List", "${this.layoutPosition}th item clicked");
                listener?.onItemClick(view, this.layoutPosition)
            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_board_list, parent, false)
        return BoardViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val item = data[position]
        holder.textViewId.text = item.boardId.toString()
        holder.textViewTitle.text = item.title.toString()
        holder.textViewContent.text = item.content.toString()
        holder.textViewSavedTime.text = item.savedTime.toString()
        holder.textViewBoardViews.text = item.boardViews.toString()

    }

    fun getItem(position: Int):MyBoard {
        return data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }


}