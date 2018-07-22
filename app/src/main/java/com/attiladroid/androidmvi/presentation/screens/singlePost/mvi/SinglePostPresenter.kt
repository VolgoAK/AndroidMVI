package com.attiladroid.androidmvi.presentation.screens.singlePost.mvi

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber

class SinglePostPresenter : MviBasePresenter<SinglePostView, SinglePostVS>(), KoinComponent {

    val interactor: SinglePostInteractor by inject()

    override fun bindIntents() {
        val observables = mutableListOf<Observable<SinglePostPartialVS>>()

        intent(SinglePostView::loadPostInitial)
                .flatMap {
                    interactor.getPost(it)
                }.apply { observables.add(this) }

        intent(SinglePostView::loadCommentInitial)
                .flatMap {
                    interactor.getComments(it)
                }.apply { observables.add(this) }

        intent(SinglePostView::refreshPostIntent)
                .flatMap {
                    interactor.getPost(it)
                }.apply { observables.add(this) }

        intent(SinglePostView::refreshCommentsIntent)
                .flatMap {
                    interactor.getComments(it)
                }.apply { observables.add(this) }

        var initialState = SinglePostVS()

        Observable.merge(observables)
                .scan(initialState, this::reduceState)
                .observeOn(AndroidSchedulers.mainThread())
                .apply { subscribeViewState(this, SinglePostView::render) }
    }

    private fun reduceState(previousState: SinglePostVS, changes: SinglePostPartialVS): SinglePostVS {
        Timber.d("changes is $changes")
        return when (changes) {
            is SinglePostPartialVS.PostDataState -> {
                previousState.copy(postLoading = false, postError = null, post = changes.post)
            }
            is SinglePostPartialVS.PostErrorState -> {
                previousState.copy(postLoading = false, postError = changes.errorMessage)
            }
            is SinglePostPartialVS.PostLoadingState -> {
                previousState.copy(postLoading = true, postError = null)
            }
            is SinglePostPartialVS.CommentsDataState -> {
                previousState.copy(commentsLoading = false, commentError = null, comments = changes.comments)
            }
            is SinglePostPartialVS.CommentErrorState -> {
                previousState.copy(commentsLoading = false, commentError = changes.errorMessage)
            }
            is SinglePostPartialVS.CommentsLoadingState -> {
                previousState.copy(commentsLoading = true, commentError = null)
            }
        }
    }
}