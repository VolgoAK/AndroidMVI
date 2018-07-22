package com.attiladroid.androidmvi.presentation.screens.singlePost.mvi

import com.attiladroid.androidmvi.data.DataSource
import io.reactivex.schedulers.Schedulers

class SinglePostInteractor(private val dataSource: DataSource) {

    fun getPost(id: Long) = dataSource.getPostById(id)
            .map { SinglePostPartialVS.PostDataState(it) }
            .cast(SinglePostPartialVS::class.java)
            .startWith(SinglePostPartialVS.PostLoadingState)
            .onErrorReturn { SinglePostPartialVS.PostErrorState(it.message ?: "") }
            .subscribeOn(Schedulers.io())

    fun getComments(id: Long) = dataSource.getComments(id)
            .map { SinglePostPartialVS.CommentsDataState(it) }
            .cast(SinglePostPartialVS::class.java)
            .startWith(SinglePostPartialVS.CommentsLoadingState)
            .onErrorReturn { SinglePostPartialVS.CommentErrorState(it.message ?: "") }
            .subscribeOn(Schedulers.io())
}