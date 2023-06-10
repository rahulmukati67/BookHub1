package com.example.bookhub1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub1.R
import com.example.bookhub1.database.BookEntity
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context, val bookList:List<BookEntity>) :RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>(){
    class FavouriteViewHolder(view: View):RecyclerView.ViewHolder(view){
        val txtBookName : TextView =view.findViewById(R.id.txtBookName)
        val txtAuthor : TextView = view.findViewById(R.id.txtBookAuthor)
        val txtBookPrice : TextView = view.findViewById(R.id.txtBookPrice)
        val txtBookRating : TextView = view.findViewById(R.id.txtBookRating)
        val imgBookImage : ImageView = view.findViewById(R.id.imgBookImage)
        val llcontent : LinearLayout =view.findViewById(R.id.llcontent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_favorite_single_row,parent,false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val book=bookList[position]
        holder.txtBookName.text = book.bookName
        holder.txtAuthor.text=book.bookAuthor
        holder.txtBookRating.text=book.bookRating
        holder.txtBookPrice.text=book.bookPrice
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgBookImage);
    }
}