package com.attiladroid.androidmvi.presentation.screens.postList

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import com.attiladroid.androidmvi.R
import com.attiladroid.androidmvi.presentation.base.BaseController
import com.attiladroid.androidmvi.data.beans.Post
import com.attiladroid.androidmvi.extensions.bindView
import com.attiladroid.androidmvi.extensions.makeGone
import com.attiladroid.androidmvi.extensions.makeVisible
import com.attiladroid.androidmvi.extensions.toast
import com.attiladroid.androidmvi.presentation.screens.postList.adapter.PostsAdapter
import com.attiladroid.androidmvi.presentation.screens.postList.mvi.PostListPresenter
import com.attiladroid.androidmvi.presentation.screens.postList.mvi.PostListVS
import com.attiladroid.androidmvi.presentation.screens.postList.mvi.PostsListView
import com.attiladroid.androidmvi.presentation.screens.singlePost.SinglePostController
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import io.reactivex.subjects.PublishSubject
import org.koin.standalone.KoinComponent
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState

class PostListContloller : BaseController<PostsListView, PostListPresenter, PostListVS>(),
        PostsListView, KoinComponent {

    private val loading by bindView<FrameLayout>(R.id.fLoading)
    private val recycler by bindView<RecyclerView>(R.id.recycler)
    private val refresh by bindView<SwipeRefreshLayout>(R.id.swipe)

    private val postsSubject = PublishSubject.create<Unit>()

    private var adapter = PostsAdapter()

    override fun onViewCreated(itemView: View) {
        initRecycler()
        refresh.setOnRefreshListener {
            postsSubject.onNext(Unit)
            refresh.isRefreshing = true
        }

        if (adapter.itemCount == 0) {
            postsSubject.onNext(Unit)
        }
    }

    private fun initRecycler() {
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
    }

    override fun getLayoutId() = R.layout.controller_post_list

    override fun createPresenter() = PostListPresenter()

    override fun render(state: BaseViewState) {
        when (state) {
            is PostListVS.DataState -> {
                refresh.isRefreshing = false
                initAdapter(state.posts)
            }
            is PostListVS.LoadingState -> {
                if (adapter.itemCount == 0) {
                    refresh.makeGone()
                    loading.makeVisible()
                }
            }
            is PostListVS.ErrorState -> {
                refresh.isRefreshing = false
                activity?.toast(state.errorMessage)
            }
        }
    }

    private fun initAdapter(postList: List<Post>) {
        loading.makeGone()
        refresh.makeVisible()
        adapter.changeData(postList)
        adapter.listener = {
            openPost(it.id)
        }
    }

    private fun openPost(id: Long) {
        router.pushController(
                RouterTransaction.with(
                        SinglePostController(id)
                ).tag(SinglePostController.TAG)
                        .pushChangeHandler(FadeChangeHandler())
                        .popChangeHandler(FadeChangeHandler())
        )
    }

    override fun loadPostsIntent() = postsSubject
}