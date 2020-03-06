package com.pram.bookapivolley.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pram.bookapivolley.R
import com.pram.bookapivolley.model.Book
import com.pram.bookapivolley.viewholder.BookViewHolder

class BookAdapter(private val mModels: MutableList<Book?>?) : RecyclerView.Adapter<BookViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val model = mModels!![position]

        holder.apply { holder
            holder.setTvBookId(model!!.id.toString())
            holder.setTvBookTitle(model.title)
            holder.setTvBookAuthor(model.author)
            holder.setTvBookPage(model.pages)
            holder.itemView.setOnClickListener {
                if (mOnItemClickListener != null) {
                    if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                        mOnItemClickListener!!.onItemClick(position)
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return mModels!!.size
    }

}