package com.attiladroid.androidmvi.presentation.screens.postList.mvi

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseView


interface PostsListView: BaseView {
    fun loadPostsIntent() : Observable<Unit>
}