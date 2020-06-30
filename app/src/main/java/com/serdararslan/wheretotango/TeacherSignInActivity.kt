package com.serdararslan.wheretotango

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.LogInCallback
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_teacher_sign_in.*
class TeacherSignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_sign_in)
        val currentUser = ParseUser.getCurrentUser()
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainPageActivity::class.java)
            intent.putExtra("username", currentUser);
            startActivity(intent)
        }

    }

    fun teacherSignInBackClick(view: View){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    fun teacherSignInSignInClick(view: View){
        ParseUser.logInInBackground(teacherSignInEmailText.text.toString(),teacherSignInPasswordText.text.toString(),
            LogInCallback { user, e ->
                if(ParseUser.getCurrentUser().get("usertype").toString().toInt() == 0){
                    ParseUser.logOut()
                    Toast.makeText(applicationContext,"You are Social Dancer, "+user.get("personname").toString(),Toast.LENGTH_LONG).show()
                } else if(e != null) {
                    Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext,"Welcome "+user.get("personname").toString(),Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext,MainPageActivity::class.java)
                    intent.putExtra("username", user);
                    startActivity(intent)
                    finish()
                }
            })
    }

}

