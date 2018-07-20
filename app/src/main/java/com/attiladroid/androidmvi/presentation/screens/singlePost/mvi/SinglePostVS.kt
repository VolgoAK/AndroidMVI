package com.attiladroid.androidmvi.presentation.screens.singlePost.mvi

import com.attiladroid.androidmvi.data.beans.Post
import com.attiladroid.androidmvi.data.beans.Comment
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState


sealed class SinglePostVS : BaseViewState{

    object LoadingState: SinglePostVS()

    data class PostDataState(var post: Post) : SinglePostVS()

    data class CommentsDataState(var comments: List<Comment>): SinglePostVS()

    data class ErrorState(var errorMessage: String): SinglePostVS()
}