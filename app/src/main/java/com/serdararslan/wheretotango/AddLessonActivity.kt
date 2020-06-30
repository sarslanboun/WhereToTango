package com.serdararslan.wheretotango

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_add_lesson.*
import java.io.ByteArrayOutputStream

//var globalObjectType = 0
    //var globalObjectName = ""
    var globalLessonTopic = ""
    var globalLessonInstructors = ""
    var globalLessonPhone = ""
    var globalAboutLesson = ""
    var globalLessonImage : Bitmap? = null

class AddLessonActivity : AppCompatActivity() {

    var chosenImage : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lesson)

        println(latitude)
        println("asdfa")

    }

    fun addLessonImageClick(view: View){

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),2)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == 2) {
            if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,1)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val selected = data.data

            try {

                chosenImage = MediaStore.Images.Media.getBitmap(this.contentResolver,selected)
                addLessonImageButton.setImageBitmap(chosenImage)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // CLICK TO ADD MAP


    fun addLessonMapClick(view: View) {

        val intent = Intent(applicationContext,MapsActivity::class.java)
        startActivity(intent)
    }

    // TIKLAYINCA DATABASE KAYIT

    fun addLessonSaveClick (view: View){
        globalLessonTopic = addLessonLessonTopicText.text.toString()
        globalLessonInstructors = addLessonInstructorsText.text.toString()
        globalLessonPhone = addLessonPhoneText.text.toString()
        globalAboutLesson = addLessonAboutText.text.toString()
        globalLessonImage = chosenImage
        globalObjectType = 2
        globalObjectName = "Lessons"

        if ( globalLessonImage == null) {
            Toast.makeText(applicationContext,"You Should Choose Image!", Toast.LENGTH_LONG).show()
        }else if ( globalLessonTopic == null || globalLessonTopic == "") {
            Toast.makeText(applicationContext,"You Should Write Name!", Toast.LENGTH_LONG).show()
        }else if ( globalLessonInstructors == null || globalLessonInstructors == "") {
            Toast.makeText(applicationContext,"You Should Write Instructors Name!", Toast.LENGTH_LONG).show()
        }else if ( globalLessonPhone == null || globalLessonPhone == "") {
            Toast.makeText(applicationContext,"You Should Write Phone!", Toast.LENGTH_LONG).show()
        }else if ( globalAboutLesson == null || globalAboutLesson == "") {
            Toast.makeText(applicationContext,"You Should Write About Lesson!", Toast.LENGTH_LONG).show()
        }else if ( latitude == null || latitude == "") {
            Toast.makeText(applicationContext,"You Should Choose a location!", Toast.LENGTH_LONG).show()
        }else if ( longitute == null || longitute == "") {
            Toast.makeText(applicationContext,"You Should Choose a location!", Toast.LENGTH_LONG).show()
        }else if( globalObjectType == 2 && globalObjectName == "Lessons"  && loc == "selected") {
            val currentUser = ParseUser.getCurrentUser()
            val parseLessons = ParseObject("Lessons")
            parseLessons.put("createdby",currentUser)
            parseLessons.put("lessonName",globalLessonTopic)
            parseLessons.put("lessonInstructors",globalLessonInstructors)
            parseLessons.put("lessonPhone",globalLessonPhone)
            parseLessons.put("aboutLesson",globalAboutLesson)
            parseLessons.put("latitude",latitude)
            parseLessons.put("longitude",longitute)
            val byteArrayOutputStream = ByteArrayOutputStream()
            globalLessonImage!!.compress(Bitmap.CompressFormat.PNG,25,byteArrayOutputStream)
            val bytes = byteArrayOutputStream.toByteArray()
            val parseFile = ParseFile("image.png",bytes)
            parseLessons.put("image",parseFile)
            parseLessons.saveInBackground { e ->
                if (e != null) {
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Location Created", Toast.LENGTH_LONG).show()
                    val intent2 = Intent(applicationContext, LessonsActivity::class.java)
                    startActivity(intent2)
                    finish()
                }
            }
        }




    }



}
