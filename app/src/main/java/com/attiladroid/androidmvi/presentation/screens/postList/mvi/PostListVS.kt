package com.attiladroid.androidmvi.presentation.screens.postList.mvi

import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState
import com.attiladroid.androidmvi.data.beans.Post

sealed class PostListVS : BaseViewState {

    object LoadingState: PostListVS()

    data class DataState(var posts: List<Post>) : PostListVS()

    data class ErrorState(var errorMessage: String): PostListVS()
}