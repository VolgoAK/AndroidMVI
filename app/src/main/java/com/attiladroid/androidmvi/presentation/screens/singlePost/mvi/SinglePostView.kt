package com.attiladroid.androidmvi.presentation.screens.singlePost.mvi

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseView

interface SinglePostView : BaseView {
    fun refreshPostIntent(): Observable<Long>
    fun refreshCommentsIntent(): Observable<Long>
    fun loadPostInitial(): Observable<Long>
    fun loadCommentInitial(): Observable<Long>
}