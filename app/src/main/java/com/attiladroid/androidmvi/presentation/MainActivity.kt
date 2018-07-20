package com.attiladroid.androidmvi.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.attiladroid.androidmvi.R
import com.attiladroid.androidmvi.presentation.screens.postList.PostListContloller
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction

class MainActivity : AppCompatActivity() {

    private lateinit var router: Router


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val container = findViewById<FrameLayout>(R.id.container)

        router = Conductor.attachRouter(this, container, savedInstanceState)

        if(!router.hasRootController()){
            router.setRoot(RouterTransaction.with(PostListContloller()))
        }
    }

    override fun onBackPressed() {
        if(!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
