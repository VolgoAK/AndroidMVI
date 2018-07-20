package com.attiladroid.androidmvi.data.beans

data class Post(var id: Long = 0,
                var title: String = "",
                var body: String = "",
                var userId: Int = 0)