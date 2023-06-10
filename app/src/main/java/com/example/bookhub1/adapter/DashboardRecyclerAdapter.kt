package com.example.bookhub1.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub1.R
import com.example.bookhub1.acivity.DescriptionActivity
import com.example.bookhub1.model.Book
import com.squareup.picasso.Picasso

class DashboardRecyclerAdapter(val context :Context ,val itemList :ArrayList<Book>) :RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {

  class DashboardViewHolder(view : View): RecyclerView.ViewHolder(view){
      val txtBookName :TextView =view.findViewById(R.id.txtBookName)
      val txtAuthor : TextView= view.findViewById(R.id.txtBookAuthor)
      val txtBookPrice : TextView= view.findViewById(R.id.txtBookPrice)
      val txtBookRating : TextView= view.findViewById(R.id.txtBookRating)

      val imgBookImage :ImageView= view.findViewById(R.id.imgBookImage)
      val llcontent : LinearLayout=view.findViewById(R.id.llcontent)
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book=itemList[position]
        holder.txtBookName.text = book.bookName
        holder.txtAuthor.text=book.bookAuthor
        holder.txtBookRating.text=book.bookRating
        holder.txtBookPrice.text=book.bookPrice
//        holder.imgBookImage.setImageResource(book.BookImage)
        Picasso.get().load(book.BookImage).error(R.drawable.default_book_cover).into(holder.imgBookImage);


        holder.llcontent.setOnClickListener{
           val intent = Intent(context,DescriptionActivity::class.java)
            intent.putExtra("bookd_id",book.bookId)
            context.startActivity(intent)
        }

    }

}























