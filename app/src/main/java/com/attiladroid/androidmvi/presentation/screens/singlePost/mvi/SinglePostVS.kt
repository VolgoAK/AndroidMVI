package com.attiladroid.androidmvi.presentation.screens.singlePost.mvi

import com.attiladroid.androidmvi.data.beans.Comment
import com.attiladroid.androidmvi.data.beans.Post
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState


data class SinglePostVS(val postLoading: Boolean = false,
                        val commentsLoading: Boolean = false,
                        val comments: List<Comment>? = null,
                        val post: Post? = null,
                        val commentError: String? = null,
                        val postError: String? = null) : BaseViewState

sealed class SinglePostPartialVS {
    object PostLoadingState : SinglePostPartialVS()

    object CommentsLoadingState : SinglePostPartialVS()

    data class CommentsDataState(val comments: List<Comment>) : SinglePostPartialVS()

    data class PostDataState(var post: Post) : SinglePostPartialVS()

    data class CommentErrorState(val errorMessage: String) : SinglePostPartialVS()

    data class PostErrorState(val errorMessage: String) : SinglePostPartialVS()
}