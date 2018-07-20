package com.attiladroid.androidmvi.presentation.screens.singlePost.mvi

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseView

interface SinglePostView : BaseView {
    fun loadPostIntent(): Observable<Long>
    fun loadCommentsIntent(): Observable<Long>
}