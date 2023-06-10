package com.example.bookhub1.acivity

import android.content.Context
import android.media.AsyncPlayer
import android.media.Image
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub1.R
import com.example.bookhub1.database.BookDatabase
import com.example.bookhub1.database.BookEntity
import com.squareup.picasso.Picasso
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {

    lateinit var txtBookName : TextView
    lateinit var txtBookAuthor: TextView
    lateinit var txtBookPrice: TextView
    lateinit var txtBookRating:TextView
    lateinit var imgBookImage :ImageView
    lateinit var txtBookDesc :TextView
    lateinit var btnAddToFav:Button
    lateinit var progressBar:ProgressBar
    lateinit var progressLayout:RelativeLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

     var bookId :String?="100"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        txtBookName = findViewById(R.id.txtBookName)
        txtBookAuthor = findViewById(R.id.txtBookAuthor)
        txtBookPrice = findViewById(R.id.txtBookPrice)
        txtBookDesc = findViewById(R.id.txtBookDesc)
        btnAddToFav = findViewById(R.id.btnToFav)
        imgBookImage = findViewById(R.id.imgBookImage)
        txtBookRating = findViewById(R.id.txtBookRating)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title="Book Details"

        if (intent != null) {
            bookId = intent.getStringExtra("book_id")

        } else {
            Toast.makeText(
                this@DescriptionActivity,
                "Some unexpected Error Occured",
                Toast.LENGTH_SHORT
            ).show()
        }
        if (bookId == "100") {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some unexpected Error Occured",
                Toast.LENGTH_SHORT
            ).show()
        }
        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)

        val jsonRequest = object :JsonObjectRequest(Request.Method.POST , url , jsonParams, Response.Listener {
            try{
                val success = it.getBoolean("success")
                if(success){
                   val bookJsonObject = it.getJSONObject("book_data")
                    progressLayout.visibility=View.GONE
                    val bookImageUrl =bookJsonObject.getString("image")
                    Picasso.get().load(bookJsonObject.getString("image")).error(R.drawable.default_book_cover).into(imgBookImage)
                    txtBookName.text=bookJsonObject.getString("name")
                    txtBookAuthor.text=bookJsonObject.getString("author")
                    txtBookPrice.text=bookJsonObject.getString("price")
                    txtBookRating.text=bookJsonObject.getString("rating")
                    txtBookDesc.text=bookJsonObject.getString("description")
                    val bookEntity=BookEntity(
                        bookId?.toInt() as Int,
                        txtBookAuthor.text.toString(),
                        txtBookDesc.text.toString(),
                        txtBookName.text.toString(),
                        txtBookRating.text.toString(),
                        txtBookPrice.text.toString(),
                        bookImageUrl
                    )
                    val checkFav = DBAsyncTask(applicationContext,bookEntity,1).execute()
                    val isFav=checkFav.get()
                    if(isFav){
                        btnAddToFav.text="Remve From fav"
                    }else{
                        btnAddToFav.text="Add to Fav"
                    }
                    btnAddToFav.setOnClickListener {
                        if (!DBAsyncTask(applicationContext, bookEntity, 1).execute().get()){
                            val async =DBAsyncTask(applicationContext,bookEntity,2).execute()
                            val result = async.get()
                            if(result){
                                Toast.makeText(this@DescriptionActivity,"Book Add To Fav",Toast.LENGTH_SHORT).show()
                                btnAddToFav.text="Remove from fav"
                            }else{
                                Toast.makeText(this@DescriptionActivity,"Some Error Occured",Toast.LENGTH_SHORT).show()
                            }

                        }else{
                            val async = DBAsyncTask(applicationContext,bookEntity,3).execute()
                            val result=async.get()
                            if(result){
                                Toast.makeText(this@DescriptionActivity,"Book Remove From Fav",Toast.LENGTH_SHORT).show()
                                btnAddToFav.text="Add To Favourites"
                            }else{
                                Toast.makeText(this@DescriptionActivity,"Some Error Occured",Toast.LENGTH_SHORT).show()
                            }


                        }
                    }

                }else{
                    Toast.makeText(
                        this@DescriptionActivity,
                        "some Error Occured!!",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }catch(e:Exception) {
                Toast.makeText(
                    this@DescriptionActivity,
                    "some Error Occured!!",
                    Toast.LENGTH_SHORT
                ).show()
            }


            }, Response.ErrorListener {
            Toast.makeText(
                this@DescriptionActivity,
                "Volley Error",
                Toast.LENGTH_SHORT
            ).show()


        }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] ="bd077d4bdd0de8"
                return headers

            }
        }

    }

    class DBAsyncTask(val context :Context,val bookEntity:BookEntity,val mode:Int): AsyncTask<Void, Void, Boolean>() {

        /* mode1->check if the boook is favourite or not
        mode2-save the book into db as favourite
        mode3->Remoce the book from favourite
         */
        val db= Room.databaseBuilder(context,BookDatabase::class.java,"book-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
           when(mode){
               1->{
                   val book: BookEntity? = db.bookDao().getBookById(bookEntity.book_id.toString())
                   db.close()
                   return book != null

               }
               2->{
                   db.bookDao().insertBook(bookEntity)
                   db.close()
                   return true

               }
               3->{
                   db.bookDao().deleteBook(bookEntity)
                   db.close()
                   return true

               }
           }
            return false
        }

    }
}