package com.attiladroid.androidmvi.data.beans

data class Comment(var name: String = "",
                   var postId: Long = 0,
                   var id: Long = 0,
                   var body: String = "",
                   var email: String = "")