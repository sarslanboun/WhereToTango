package com.serdararslan.wheretotango

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser


class MainPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
    }


    fun mainPageSchoolsClick(view: View){
        val intent = Intent(applicationContext, SchoolsActivity::class.java)
        startActivity(intent)
    }
    fun mainPageLessonsClick(view: View){
        val intent = Intent(applicationContext, LessonsActivity::class.java)
        startActivity(intent)
    }
    fun mainPageMilongasClick(view: View){
        val intent = Intent(applicationContext, MilongasActivity::class.java)
        startActivity(intent)
    }
    fun mainPageFestivalsClick(view: View){
        val intent = Intent(applicationContext, FestivalsActivity::class.java)
        startActivity(intent)
    }
    fun mainPageStoresClick(view: View){
        val intent = Intent(applicationContext, StoresActivity::class.java)
        startActivity(intent)
    }


    fun mainPageProfileClick(view: View){
        //val intent = Intent(applicationContext, ProfileActivity::class.java)
        //startActivity(intent)
        ParseUser.logOut()
        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
