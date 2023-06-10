package com.example.bookhub1.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bookhub1.model.Book

@Dao
interface BookDao {

    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks():List<BookEntity>

    @Query("SELECT * FROM books WHERE book_id = :bookID")
    fun getBookById(bookId:String):BookEntity

}