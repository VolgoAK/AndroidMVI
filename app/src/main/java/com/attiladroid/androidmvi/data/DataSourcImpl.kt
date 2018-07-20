package com.attiladroid.androidmvi.data

import com.attiladroid.androidmvi.data.beans.Comment
import com.attiladroid.androidmvi.data.beans.Post
import com.attiladroid.androidmvi.domain.PostsApi
import io.reactivex.Observable

class DataSourcImpl(val api: PostsApi): DataSource {

    override fun getAllPosts(): Observable<List<Post>> {
        return api.getAllPosts()
    }

    override fun getPostById(id: Long): Observable<Post> {
        return api.getPostById(id)
    }

    override fun getComments(postId: Long): Observable<List<Comment>> {
        return api.getCommentsByPostId(postId)
    }
}