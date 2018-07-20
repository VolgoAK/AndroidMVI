package com.attiladroid.androidmvi.data

import com.attiladroid.androidmvi.data.beans.Comment
import com.attiladroid.androidmvi.data.beans.Post
import io.reactivex.Observable

interface DataSource {
    fun getAllPosts() : Observable<List<Post>>

    fun getPostById(id: Long): Observable<Post>

    fun getComments(postId: Long): Observable<List<Comment>>
}