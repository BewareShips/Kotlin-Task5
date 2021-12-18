package com.example.kotlintask_5.room

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "books_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var author: String = "",
    val country: String = "",
    val imageLink: String = "",
    val language: String = "",
    val link: String = "",
    val pages: Int = 0,
    var title: String = "",
    var year: Int = 0
)
