package com.serdararslan.wheretotango

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.LogInCallback
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_dancer_sign_in.*

class DancerSignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dancer_sign_in)
        val currentUser = ParseUser.getCurrentUser()
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainPageActivity::class.java)
            intent.putExtra("username", currentUser);
            startActivity(intent)
        }
    }
    fun dancerSignInBackClick(view: View){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    fun dancerSignInSignInClick(view: View){
        ParseUser.logInInBackground(dancerSignInEmailText.text.toString(),dancerSignInPasswordText.text.toString(),
            LogInCallback { user, e ->
                if(ParseUser.getCurrentUser().get("usertype").toString().toInt() == 1){
                    ParseUser.logOut()
                    Toast.makeText(applicationContext,"You are not Social Dancer, "+user.get("personname").toString(),Toast.LENGTH_LONG).show()
                } else if(e != null) {
                    Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext,"Welcome "+user.get("personname").toString(),
                        Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext,MainPageActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })

    }
}
