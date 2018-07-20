package com.attiladroid.androidmvi.presentation.screens.postList.mvi

import com.attiladroid.androidmvi.data.DataSource

class PostListInteractor(val dataSource: DataSource) {

    fun getPosts() = dataSource.getAllPosts()
}