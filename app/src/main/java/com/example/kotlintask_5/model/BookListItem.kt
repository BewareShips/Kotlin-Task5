package com.example.kotlintask_5.model

data class BookListItem(
    var author: String = "",
    var country: String = "",
    var imageLink: String = "",
    var language: String = "",
    var link: String = "",
    var pages: Int = 0,
    var title: String = "",
    var year: Int = 0
)