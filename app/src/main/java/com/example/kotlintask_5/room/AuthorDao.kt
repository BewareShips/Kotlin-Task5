package com.example.kotlintask_5.room

import androidx.room.*



@Dao
interface AuthorDao {
    @Query("SELECT * FROM author_table")
    fun getAll(): List<Author>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAuthors(vararg authors: Author)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBooks(vararg books: Book)

    @Transaction
    @Query("SELECT * FROM author_table WHERE author = :author")
    suspend fun getAuthorWithBooks(author: String): List<AuthorAndBooks>
}
