package com.attiladroid.androidmvi.presentation.screens.singlePost

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.attiladroid.androidmvi.R
import com.attiladroid.androidmvi.data.beans.Comment
import com.attiladroid.androidmvi.presentation.base.BaseController
import com.attiladroid.androidmvi.data.beans.Post
import com.attiladroid.androidmvi.extensions.bindView
import com.attiladroid.androidmvi.extensions.makeGone
import com.attiladroid.androidmvi.extensions.makeVisible
import com.attiladroid.androidmvi.extensions.toast
import com.attiladroid.androidmvi.presentation.screens.singlePost.adapter.CommentsAdapter
import com.attiladroid.androidmvi.presentation.screens.singlePost.mvi.SinglePostPresenter
import com.attiladroid.androidmvi.presentation.screens.singlePost.mvi.SinglePostVS
import com.attiladroid.androidmvi.presentation.screens.singlePost.mvi.SinglePostView
import io.reactivex.subjects.PublishSubject
import org.koin.standalone.KoinComponent
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState
import timber.log.Timber

class SinglePostController : BaseController<SinglePostView, SinglePostPresenter, SinglePostVS>,
        SinglePostView, KoinComponent {

    companion object {
        const val TAG = "SinglePostController"
    }

    private val tvDate by bindView<TextView>(R.id.tvDate)
    private val tvTitle by bindView<TextView>(R.id.tvTitle)
    private val tvContent by bindView<TextView>(R.id.tvContent)
    private val rvComments by bindView<RecyclerView>(R.id.rvComments)
    private val loading by bindView<FrameLayout>(R.id.loading)

    private val commentsAdapter = CommentsAdapter()

    private var postId = 0L
    private var post: Post? = null
    private var commentsLoaded = false

    constructor() : super()

    constructor(postId: Long) : super() {
        this.postId = postId
    }

    private val loadPostSubject = PublishSubject.create<Long>()
    private val loadCommentsSubject = PublishSubject.create<Long>()

    override fun onViewCreated(itemView: View) {
        initRecycler()
        if(post != null) {
           onPostReady(post!!)
        } else {
            loadPostSubject.onNext(postId)
        }
        if(!commentsLoaded) loadCommentsSubject.onNext(postId)
    }

    private fun initRecycler() {
        rvComments.layoutManager = LinearLayoutManager(activity)
        rvComments.adapter = commentsAdapter
    }

    override fun getLayoutId() = R.layout.controller_post

    override fun createPresenter() = SinglePostPresenter()

    override fun loadPostIntent() = loadPostSubject

    override fun loadCommentsIntent() = loadCommentsSubject

    override fun render(state: BaseViewState) {
        when (state) {
            is SinglePostVS.PostDataState -> {
                loading.makeGone()
                post = state.post
                onPostReady(state.post)
            }
            is SinglePostVS.CommentsDataState -> {
                onCommentsReady(state.comments)
            }
            is SinglePostVS.ErrorState -> {
                loading.makeGone()
                activity?.toast(state.errorMessage)
            }
            is SinglePostVS.LoadingState -> {
                if(post == null) loading.makeVisible()
            }
        }
    }

    private fun onCommentsReady(comments: List<Comment>) {
        commentsLoaded = true
        commentsAdapter.setData(comments)
    }

    private fun onPostReady(post: Post) {
        tvDate.text = "12.04.1984"
        tvTitle.text = post.title
        tvContent.text = post.body
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("DESTROY")
    }

}