package com.attiladroid.androidmvi.presentation.screens.postList.mvi

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class PostListPresenter : MviBasePresenter<PostsListView, PostListVS>(), KoinComponent {

    val interactor: PostListInteractor by inject()

    override fun bindIntents() {
        val state: Observable<PostListVS> =
                intent(PostsListView::loadPostsIntent)
                        .flatMap {
                            interactor.getPosts()
                                    .map { PostListVS.DataState(it) }
                                    .cast(PostListVS::class.java)
                                    .startWith(PostListVS.LoadingState)
                                    .subscribeOn(Schedulers.io())
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn {
                            PostListVS.ErrorState(it.message
                                    ?: "")
                        }



        subscribeViewState(state, PostsListView::render)
    }
}