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
import kotlinx.android.synthetic.main.activity_add_milonga.*
import java.io.ByteArrayOutputStream


//var globalObjectType = 0
//var globalObjectName = ""
var globalMilongaName = ""
var globalMilongaPrice = ""
var globalMilongaPhone = ""
var globalMilongaAbout = ""
var globalMilongaImage : Bitmap? = null


class AddMilongaActivity : AppCompatActivity() {

    var chosenImage : Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_milonga)
    }

    fun addMilongaImageClick(view: View){

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
                addMilongaImageButton.setImageBitmap(chosenImage)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // CLICK TO ADD MAP

    fun addMilongaMapClick(view: View) {

        val intent = Intent(applicationContext,MapsActivity::class.java)
        startActivity(intent)
    }

    // TIKLAYINCA DATABASE KAYIT

    fun addMilongaSaveClick(view: View) {

        globalMilongaName = addMilongaNameText.text.toString()
        globalMilongaPrice = addMilongaPriceText.text.toString()
        globalMilongaPhone = addMilongaPhoneText.text.toString()
        globalMilongaAbout = addMilongaAboutText.text.toString()
        globalMilongaImage = chosenImage
        globalObjectType = 3
        globalObjectName = "Milongas"


        if ( globalMilongaImage == null) {
            Toast.makeText(applicationContext,"You Should Choose Image!", Toast.LENGTH_LONG).show()
        } else if ( globalMilongaName == null || globalMilongaName == "") {
            Toast.makeText(applicationContext,"You Should Write Name!", Toast.LENGTH_LONG).show()
        }else if ( globalMilongaPrice == null || globalMilongaPrice == "") {
            Toast.makeText(applicationContext,"You Should Write Price!", Toast.LENGTH_LONG).show()
        }else if ( globalMilongaPhone == null || globalMilongaPhone == "") {
            Toast.makeText(applicationContext,"You Should Write Phone!", Toast.LENGTH_LONG).show()
        }else if ( globalMilongaAbout == null || globalMilongaAbout == "") {
            Toast.makeText(applicationContext,"You Should Write About Milonga!", Toast.LENGTH_LONG).show()
        }else if ( latitude == null || latitude == "") {
            Toast.makeText(applicationContext,"You Should Choose a location!", Toast.LENGTH_LONG).show()
        }else if ( longitute == null || longitute == "") {
            Toast.makeText(applicationContext,"You Should Choose a location!", Toast.LENGTH_LONG).show()
        } else if ( globalObjectType == 3 && globalObjectName == "Milongas" && loc == "selected") {
            val currentUser = ParseUser.getCurrentUser()
            val parseMilongas = ParseObject("Milongas")
            parseMilongas.put("createdby",currentUser)
            parseMilongas.put("milongaName",globalMilongaName)
            parseMilongas.put("milongaPrice",globalMilongaPrice)
            parseMilongas.put("milongaPhone",globalMilongaPhone)
            parseMilongas.put("milongaAbout",globalMilongaAbout)
            parseMilongas.put("latitude",latitude)
            parseMilongas.put("longitude",longitute)
            val byteArrayOutputStream = ByteArrayOutputStream()
            globalMilongaImage!!.compress(Bitmap.CompressFormat.PNG,25,byteArrayOutputStream)
            val bytes = byteArrayOutputStream.toByteArray()
            val parseFile = ParseFile("image.png",bytes)
            parseMilongas.put("image",parseFile)
            parseMilongas.saveInBackground { e ->
                if (e != null) {
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Location Created", Toast.LENGTH_LONG).show()
                    val intent3 = Intent(applicationContext, MilongasActivity::class.java)
                    startActivity(intent3)
                    finish()
                }

            }



        }




    }


}
