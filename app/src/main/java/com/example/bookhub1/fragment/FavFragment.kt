package com.example.bookhub1.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.bookhub1.R
import com.example.bookhub1.adapter.FavouriteRecyclerAdapter
import com.example.bookhub1.database.BookDatabase
import com.example.bookhub1.database.BookEntity
import com.example.bookhub1.model.Book

class FavFragment : Fragment() {
    lateinit var recyclerFavourite :RecyclerView
    lateinit var progressLayout :RelativeLayout
    lateinit var progressBar:ProgressBar
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavouriteRecyclerAdapter
   var dbBookList= listOf<BookEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view =inflater.inflate(R.layout.fragment_fav, container, false)
        recyclerFavourite=view.findViewById(R.id.recyclerFavourite)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)

        layoutManager=GridLayoutManager(activity as Context,2)
        dbBookList=RetrieveFavourites(activity as Context).execute().get()
        if(activity !=null){
            progressLayout.visibility= View.GONE
            recyclerAdapter= FavouriteRecyclerAdapter(activity as Context,dbBookList)
            recyclerFavourite.adapter=recyclerAdapter
            recyclerFavourite.layoutManager=layoutManager
        }
        return view
    }

     class RetrieveFavourites(val context:Context):AsyncTask<Void,Void,List<BookEntity>>(){
         @SuppressLint("SuspiciousIndentation")
         override fun doInBackground(vararg params: Void?): List<BookEntity> {
         val db= Room.databaseBuilder(context,BookDatabase::class.java,"book_db").build()
             return db.bookDao().getAllBooks()
         }


     }


}