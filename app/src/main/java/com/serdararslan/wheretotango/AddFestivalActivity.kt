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
import kotlinx.android.synthetic.main.activity_add_festival.*
import java.io.ByteArrayOutputStream


//var globalObjectType = 0
    //var globalObjectName = ""
    var globalFestivalName = ""
    var globalFestivalCity = ""
    var globalFestivalStartDate =""
    var globalFestivalEndDate = ""
    var globalFestivalPhone = ""
    var globalFestivalAbout = ""
    var globalFestivalImage : Bitmap? = null


class AddFestivalActivity : AppCompatActivity() {

    var chosenImage : Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_festival)
    }

    fun addFestivalImageClick(view: View){

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
                addFestivalImageButton.setImageBitmap(chosenImage)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // CLICK TO ADD MAP

    fun addFestivalMapClick(view: View) {

        val intent = Intent(applicationContext,MapsActivity::class.java)
        startActivity(intent)

    }


    // TIKLAYINCA DATABASE KAYIT


    fun addFestivalSaveClick ( view: View) {

        globalFestivalName = addFestivalNameText.text.toString()
        globalFestivalCity = addFestivalCityText.text.toString()
        globalFestivalStartDate = addFestivalStartDateText.text.toString()
        globalFestivalEndDate = addFestivalEndDateText.text.toString()
        globalFestivalPhone = addFestivalPhoneText.text.toString()
        globalFestivalAbout = addFestivalAboutText.text.toString()
        globalFestivalImage = chosenImage
        globalObjectType = 4
        globalObjectName = "Festivals"


        if ( globalFestivalImage == null) {
            Toast.makeText(applicationContext,"You Should Choose Image!", Toast.LENGTH_LONG).show()
        } else if ( globalFestivalName == null || globalFestivalName == "") {
            Toast.makeText(applicationContext,"You Should Write Name!", Toast.LENGTH_LONG).show()
        } else if ( globalFestivalCity == null || globalFestivalCity == "") {
            Toast.makeText(applicationContext,"You Should Write City!", Toast.LENGTH_LONG).show()
        }else if ( globalFestivalStartDate == null || globalFestivalStartDate == "") {
            Toast.makeText(applicationContext,"You Should Write Start Date!", Toast.LENGTH_LONG).show()
        }else if ( globalFestivalEndDate == null || globalFestivalEndDate == "") {
            Toast.makeText(applicationContext,"You Should Write End Date!", Toast.LENGTH_LONG).show()
        } else if ( globalFestivalPhone == null || globalFestivalPhone == "") {
            Toast.makeText(applicationContext,"You Should Write Phone!", Toast.LENGTH_LONG).show()
        } else if ( globalFestivalAbout == null || globalFestivalAbout == "") {
            Toast.makeText(applicationContext,"You Should Write About!", Toast.LENGTH_LONG).show()
        } else if ( latitude == null || latitude == "") {
            Toast.makeText(applicationContext,"You Should Choose a location!", Toast.LENGTH_LONG).show()
        } else if ( longitute == null || longitute == "") {
            Toast.makeText(applicationContext,"You Should Choose a location!", Toast.LENGTH_LONG).show()
        } else if ( globalObjectType == 4 && globalObjectName == "Festivals"  && loc == "selected") {
            val currentUser = ParseUser.getCurrentUser()
            val parseFestivals = ParseObject("Festivals")
            parseFestivals.put("createdby",currentUser)
            parseFestivals.put("festivalName",globalFestivalName)
            parseFestivals.put("festivalCity",globalFestivalCity)
            parseFestivals.put("festivalStartDate",globalFestivalStartDate)
            parseFestivals.put("festivalEndDate",globalFestivalEndDate)
            parseFestivals.put("festivalPhone",globalFestivalPhone)
            parseFestivals.put("festivalAbout",globalFestivalAbout)
            parseFestivals.put("latitude",latitude)
            parseFestivals.put("longitude",longitute)
            val byteArrayOutputStream = ByteArrayOutputStream()
            globalFestivalImage!!.compress(Bitmap.CompressFormat.PNG,25,byteArrayOutputStream)
            val bytes = byteArrayOutputStream.toByteArray()
            val parseFile = ParseFile("image.png",bytes)
            parseFestivals.put("image",parseFile)
            parseFestivals.saveInBackground { e ->
                if (e != null) {
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Location Created", Toast.LENGTH_LONG).show()
                    val intent4 = Intent(applicationContext, FestivalsActivity::class.java)
                    startActivity(intent4)
                    finish()
                }
            }
        }






    }

}
