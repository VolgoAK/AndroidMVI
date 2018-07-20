package com.attiladroid.androidmvi.presentation.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.attiladroid.androidmvi.extensions.KotterKnife
import com.bluelinelabs.conductor.Controller
import com.hannesdorfmann.mosby3.MviController
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseView
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState

abstract class BaseController<V : BaseView, P : MviPresenter<V, VS>, VS : BaseViewState> :
        MviController<V, P>() {

    private val postCreateViewListener: Controller.LifecycleListener =
            object : Controller.LifecycleListener() {
                override fun postCreateView(controller: Controller, view: View) {
                    onViewCreated(view)
                }
            }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setRetainMode()
        //set lifecycle listener
        addLifecycleListener(postCreateViewListener)
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onDestroyView(view: View) {
        //remove lifecycle listener
        removeLifecycleListener(postCreateViewListener)
        KotterKnife.reset(this)
        super.onDestroyView(view)
    }

    abstract fun onViewCreated(itemView: View)

    abstract fun getLayoutId(): Int

    open fun setRetainMode() {
        retainViewMode = RetainViewMode.RELEASE_DETACH
    }
}