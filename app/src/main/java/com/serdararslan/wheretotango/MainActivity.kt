package com.serdararslan.wheretotango

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentUser = ParseUser.getCurrentUser()
        if (currentUser != null)
        {
            val intent = Intent(applicationContext,MainPageActivity::class.java)
            intent.putExtra("username", currentUser);
            startActivity(intent)
        }

    }


    fun mainActivityTeacherSignInClick(view: View) {
        val intent = Intent(applicationContext, TeacherSignInActivity::class.java)
        startActivity(intent)
    }

    fun mainActivityTeacherRegisterClick(view: View){
        val intent = Intent(applicationContext, TeacherRegisterActivity::class.java)
        startActivity(intent)
    }

    fun mainActivityDancerSignInClick(view: View){
        val intent = Intent(applicationContext, DancerSignInActivity::class.java)
        startActivity(intent)
    }

    fun mainActivityDancerRegisterClick(view: View) {
        val intent = Intent(applicationContext, DancerRegisterActivity::class.java)
        startActivity(intent)
    }
}
