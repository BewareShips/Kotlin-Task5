package com.example.kotlintask_5.room

import androidx.room.Embedded
import androidx.room.Relation

data class AuthorAndBooks(
    @Embedded val author: Author,
    @Relation(
        parentColumn = "author",
        entityColumn = "author"
    )
    val books: List<Book>
)