package com.example.bookhub1.acivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.bookhub1.*
import com.example.bookhub1.fragment.AboutFragment
import com.example.bookhub1.fragment.Dashboard
import com.example.bookhub1.fragment.FavFragment
import com.example.bookhub1.fragment.ProfileFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar  = findViewById(R.id.toolbar)
        frameLayout  = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationLView)
        setUpToolbar()
        var previousMenuItem :MenuItem?= null

        onDasboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity , drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener(){

          if(previousMenuItem !=null){
              previousMenuItem?.isChecked=false
          }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it

            when(it.itemId){
                R.id.dashboard ->{
                    onDasboard()
                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())
                        .commit()
                        supportActionBar?.title="Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.about ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, AboutFragment())
                        .commit()
                    supportActionBar?.title="About"
                    drawerLayout.closeDrawers()
                }
                R.id.fav ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavFragment())
                        .commit()
                    supportActionBar?.title="Favorite"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="BookHub"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        if(id== android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun onDasboard(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, Dashboard())
            .commit()
        supportActionBar?.title="Dasboard"
        drawerLayout.closeDrawers()

    }

    override fun onBackPressed() {

        val frag = supportFragmentManager.findFragmentById(R.id.frame)
        when(frag){
            !is Dashboard ->onDasboard()
            else-> super.onBackPressed()

        }

    }

}