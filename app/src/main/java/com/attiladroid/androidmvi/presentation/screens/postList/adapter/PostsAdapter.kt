package com.attiladroid.androidmvi.presentation.screens.postList.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.attiladroid.androidmvi.R
import com.attiladroid.androidmvi.data.beans.Post

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    private var data: List<Post>? = null

    var listener: (Post) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_post, parent, false)

        val holder = PostViewHolder(view)
        holder.listener = listener

        return holder
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        data?.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    fun changeData(newData: List<Post>?) {
        data = newData
        notifyDataSetChanged()
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.findViewById(R.id.tvPostTitle)
        private val tvDate: TextView = view.findViewById(R.id.tvPostDate)
        private val tvContent: TextView = view.findViewById(R.id.tvPostContent)
        private val tvCommentsCount: TextView = view.findViewById(R.id.tvPostCommentsCount)

        var listener: ((Post) -> Unit)? = null

        fun bind(post: Post) {
            tvTitle.text = post.title
            tvContent.text = post.body
//            tvCommentsCount.text = post.getCommentsCount().toString()
            tvDate.setText(R.string.date_placeholder)

            itemView.setOnClickListener {
                listener?.invoke(post)
            }
        }
    }
}