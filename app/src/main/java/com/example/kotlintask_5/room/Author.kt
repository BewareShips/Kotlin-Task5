package com.example.kotlintask_5.room

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "author_table")
data class Author(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var author: String = "",
    @Ignore var country: String = "",
    @Ignore var imageLink: String = "",
    @Ignore var language: String = "",
    @Ignore var link: String = "",
    @Ignore var pages: Int = 0,
    @Ignore var title: String = "",
    @Ignore var year: Int = 0
)