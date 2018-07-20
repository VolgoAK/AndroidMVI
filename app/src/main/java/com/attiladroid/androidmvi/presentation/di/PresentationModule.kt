package com.attiladroid.androidmvi.presentation.di

import com.attiladroid.androidmvi.presentation.screens.postList.mvi.PostListInteractor
import com.attiladroid.androidmvi.presentation.screens.singlePost.mvi.SinglePostInteractor
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

val presentationModule: Module = applicationContext {
    context("PostList"){
        bean { PostListInteractor(get()) }
    }

    context("SinglePost") {
        bean { SinglePostInteractor(get()) }
    }
}