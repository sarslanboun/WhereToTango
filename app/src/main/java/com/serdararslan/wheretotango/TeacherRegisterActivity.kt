package com.serdararslan.wheretotango

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_teacher_register.*

class TeacherRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_register)
        val currentUser = ParseUser.getCurrentUser()
        if (currentUser != null)
        {
            val intent = Intent(applicationContext,MainPageActivity::class.java)
            intent.putExtra("username", currentUser);
            startActivity(intent)
        }

    }

    fun teacherRegisterBackClick(view: View){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    fun teacherRegisterRegisterClick(view: View){
        if (teacherRegisterPasswordText.text.toString() == teacherRegisterPasswordConfirmText.text.toString()) {
            val user = ParseUser()
            user.put("usertype",1)
            user.username = teacherRegisterEmailText.text.toString()
            user.setPassword(teacherRegisterPasswordText.text.toString())
            user.put("personname", teacherRegisterNameText.text.toString())


            user.signUpInBackground { e: ParseException? ->
                if(e != null) {
                    Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext,"User Created!".toString(),Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext,MainPageActivity::class.java)

                    startActivity(intent)
                    finish()
                }
            }



        } else {
            Toast.makeText(applicationContext, "Passwords are not the same!".toString(),Toast.LENGTH_LONG).show()
        }
    }
}
