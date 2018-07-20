package com.attiladroid.androidmvi.presentation.screens.singlePost.mvi

import com.attiladroid.androidmvi.data.DataSource

class SinglePostInteractor(private val dataSource: DataSource) {

    fun getPost(id: Long) = dataSource.getPostById(id)

    fun getComments(id: Long) = dataSource.getComments(id)
}