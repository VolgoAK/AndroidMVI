package com.attiladroid.androidmvi.presentation.screens.singlePost.mvi

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SinglePostPresenter : MviBasePresenter<SinglePostView, SinglePostVS>(), KoinComponent {

    val interactor: SinglePostInteractor by inject()

    override fun bindIntents() {
        val observables = mutableListOf<Observable<SinglePostVS>>()

        intent(SinglePostView::loadPostIntent)
                .flatMap {
                    interactor.getPost(it)
                            .map { SinglePostVS.PostDataState(it) }
                            .cast(SinglePostVS::class.java)
                            .startWith(SinglePostVS.LoadingState)
                            .subscribeOn(Schedulers.io())
                }.apply { observables.add(this) }

        intent(SinglePostView::loadCommentsIntent)
                .flatMap {
                    interactor.getComments(it)
                            .map { SinglePostVS.CommentsDataState(it) }
                            .cast(SinglePostVS::class.java)
                            .subscribeOn(Schedulers.io())
                }.apply { observables.add(this) }


        Observable.merge(observables)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn {
                    SinglePostVS.ErrorState(it.message
                            ?: "")
                }
                .apply {
                    subscribeViewState(this, SinglePostView::render)
                }
    }
}