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
import kotlinx.android.synthetic.main.activity_add_school.*
import java.io.ByteArrayOutputStream

var globalObjectType = 0
    var globalObjectName = ""
    var globalSchoolName = ""
    var globalSchoolPhone = ""
    var globalSchoolAbout = ""
    var globalSchoolImage : Bitmap? = null


class AddSchoolActivity : AppCompatActivity() {

    var chosenImage : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_school)
        val currentUser = ParseUser.getCurrentUser()
    }

    fun addSchoolImageClick(view: View){

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
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
                addSchoolImageButton.setImageBitmap(chosenImage)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    // CLICK TO ADD MAP

    fun addSchoolMapClick(view: View) {

        val intent = Intent(applicationContext,MapsActivity::class.java)
        startActivity(intent)
    }

            //    // TIKLAYINCA DATABASE KAYIT

    fun addSchoolSaveClick ( view: View) {

        globalSchoolName = addSchoolNameText.text.toString()
        globalSchoolPhone = addSchoolTelephoneText.text.toString()
        globalSchoolAbout = addSchoolAboutText.text.toString()
        globalSchoolImage = chosenImage
        globalObjectType = 1
        globalObjectName = "Schools"


        if ( globalSchoolImage == null) {
            Toast.makeText(applicationContext,"You Should Choose Image!", Toast.LENGTH_LONG).show()
        }else if ( globalSchoolName == null || globalSchoolName == "") {
            Toast.makeText(applicationContext,"You Should Write Name!", Toast.LENGTH_LONG).show()
        }else if ( globalSchoolPhone == null || globalSchoolPhone == "") {
            Toast.makeText(applicationContext,"You Should Write Phone!", Toast.LENGTH_LONG).show()
        }else if ( globalSchoolAbout == null || globalSchoolAbout == "") {
            Toast.makeText(applicationContext,"You Should Write About School!", Toast.LENGTH_LONG).show()
        }else if ( latitude == null || latitude == "") {
            Toast.makeText(applicationContext,"You Should Choose a location!", Toast.LENGTH_LONG).show()
        }else if ( longitute == null || longitute == "") {
            Toast.makeText(applicationContext,"You Should Choose a location!", Toast.LENGTH_LONG).show()
        } else if ( globalObjectType == 1 && globalObjectName == "Schools" && loc == "selected") {
            val currentUser = ParseUser.getCurrentUser()
            val parseSchools = ParseObject("Schools")

            parseSchools.put("createdby", currentUser)
            parseSchools.put("schoolName", globalSchoolName)
            parseSchools.put("schoolPhone", globalSchoolPhone)
            parseSchools.put("schoolAbout", globalSchoolAbout)
            parseSchools.put("latitude", latitude)
            parseSchools.put("longitude", longitute)

            val byteArrayOutputStream = ByteArrayOutputStream()
            globalSchoolImage!!.compress(Bitmap.CompressFormat.PNG,25,byteArrayOutputStream)
            val bytes = byteArrayOutputStream.toByteArray()
            val parseFile = ParseFile("image.png",bytes)
            parseSchools.put("image",parseFile)
            parseSchools.saveInBackground { e ->
                if (e != null) {
                    Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext,"Location Created", Toast.LENGTH_LONG).show()
                    val intent1 = Intent(applicationContext,SchoolsActivity::class.java)
                    startActivity(intent1)
                    finish()
                }
            }



        }






    }


}
