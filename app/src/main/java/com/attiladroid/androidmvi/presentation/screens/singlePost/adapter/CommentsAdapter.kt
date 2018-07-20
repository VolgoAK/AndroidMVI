package com.attiladroid.androidmvi.presentation.screens.singlePost.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.attiladroid.androidmvi.R
import com.attiladroid.androidmvi.data.beans.Comment
import com.attiladroid.androidmvi.extensions.bindView
import com.attiladroid.androidmvi.extensions.inflate

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.Holder>() {

    private val data = mutableListOf<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.comment_holder))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
    }

    fun setData(comments: List<Comment>) {
        data.clear()
        data.addAll(comments)
        notifyDataSetChanged()
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvAuthor by bindView<TextView>(R.id.tvCommentAuthor)
        private val tvDate by bindView<TextView>(R.id.tvCommentDate)
        private val tvContent by bindView<TextView>(R.id.tvCommentContent)

        fun bind(comment: Comment) {
            tvAuthor.text = comment.name
            tvDate.text = "12.03.1984"
            tvContent.text = comment.body
        }
    }
}