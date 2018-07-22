package com.attiladroid.androidmvi.extensions


import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.setVisibility(visible: Boolean) {
    this.visibility = if(visible) View.VISIBLE else View.GONE
}

fun ViewGroup.inflate(@LayoutRes id: Int, attachToParent: Boolean = false): View {
    return LayoutInflater.from(this.context).inflate(id, this, false)
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}