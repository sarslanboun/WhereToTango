package com.serdararslan.wheretotango

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_dancer_register.*

class DancerRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dancer_register)
        val currentUser = ParseUser.getCurrentUser()
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainPageActivity::class.java)
            intent.putExtra("username", currentUser);
            startActivity(intent)
        }
    }

    fun dancerRegisterBackClick(view: View){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    fun dancerRegisterRegisterClick(view: View){
        if (dancerRegisterPasswordText.text.toString() == dancerPasswordConfirmText.text.toString()) {
            val user = ParseUser()
            user.put("usertype",0)
            user.username = dancerRegisterEmailText.text.toString()
            user.setPassword(dancerRegisterPasswordText.text.toString())
            user.put("personname", dancerRegisterNameText.text.toString())


            user.signUpInBackground { e: ParseException? ->
                if(e != null) {
                    Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext,"User Created!".toString(), Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext,MainPageActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }



        } else {
            Toast.makeText(applicationContext, "Passwords are not the same!".toString(), Toast.LENGTH_LONG).show()
        }

    }

}
