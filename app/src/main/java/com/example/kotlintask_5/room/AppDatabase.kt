package com.example.kotlintask_5.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Author::class, Book::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authorDao(): AuthorDao
}