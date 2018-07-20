package com.attiladroid.androidmvi.domain

import com.attiladroid.androidmvi.data.beans.Comment
import com.attiladroid.androidmvi.data.beans.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsApi {

    @GET(value = "posts")
    fun getAllPosts(): Observable<List<Post>>

    @GET(value = "posts/{id}")
    fun getPostById(@Path("id") id: Long): Observable<Post>

    @GET(value = "comments")
    fun getComments(): Observable<List<Comment>>

    @GET(value = "comments")
    fun getCommentsByPostId(@Query("postId") id: Long): Observable<List<Comment>>
}