package com.example.kotlintask_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import com.example.kotlintask_5.room.AppDatabase
import com.example.kotlintask_5.room.Author
import com.example.kotlintask_5.room.AuthorAndBooks
import com.example.kotlintask_5.room.Book

import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val author = findViewById<TextInputLayout>(R.id.langTextInputView)
        val year = findViewById<TextInputLayout>(R.id.yearTextInputView)

        val button = findViewById<Button>(R.id.confirmButton)
        val roomButton = findViewById<Button>(R.id.roomData)

        val resultQuantity = findViewById<TextView>(R.id.resultQuantity)
        val resultItems = findViewById<TextView>(R.id.resultItems)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"

        ).build()

        val authorDao = db.authorDao()
        val myApplication = application as MyApplication
        val httpApiService = myApplication.httpApiService

        CoroutineScope(Dispatchers.IO).launch {
            val decodedJsonResult = httpApiService.getAllBooks()
            val results = decodedJsonResult.toList()
            var x: Int = 1
            val authorsList = results
                .distinctBy { it.author }
                .map { Author(x++, it.author)  }
            var y: Int = 1
            val booksList = results
                //.distinctBy {it.title}
                .map { Book(y++, it.author, it.country,it.imageLink, it.language, it.link, it.pages, it.title, it.year) }


            withContext(Dispatchers.Main){
                resultQuantity.text = null
                authorDao.insertAllAuthors(*authorsList.toTypedArray())
                authorDao.insertAllBooks(*booksList.toTypedArray())
            }
        }

        roomButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val authorDao = db.authorDao()
                val authorsCor = deleteDatabase("database-name");

                withContext(Dispatchers.Main){
                    resultQuantity.text = "Nuked"
                }
            }
        }
        button.setOnClickListener{
            var year = year.editText?.text?.toString()
            var author = author.editText?.text?.toString()

            val authorDao = db.authorDao()

            var maskedYear = if (year.isNullOrEmpty()) 2022
            else Integer.valueOf(year)
            var maskedAuthor = if (author.isNullOrEmpty()) ""
            else author

            CoroutineScope(Dispatchers.IO).launch {

                val booksAsString = StringBuilder("")

                val result : List<AuthorAndBooks> =  authorDao.getAuthorWithBooks(maskedAuthor)

                var booksAfterFilter = if (result.isNullOrEmpty() || result[0].books.isNullOrEmpty())
                    listOf()
                else result[0].books

                if (booksAfterFilter.isNullOrEmpty())
                    booksAsString.append("Sorry, there is nothing for this request. " +
                            "Try other options.")
                else {
                    if (maskedYear != 2022)
                        booksAfterFilter = result[0].books.filter { it.year >= maskedYear }

                    var x: Int = 1
                    for (item in booksAfterFilter.take(3))
                        booksAsString.append((x++).toString() + ") "+ item.title + " #" + item.id +"\n")
                }

                withContext(Dispatchers.Main){
                    resultItems.text = booksAsString
                    resultQuantity.text = booksAfterFilter.size.toString() + " book(s) by " + author
                }
            }
        }
    }
}