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
import kotlinx.android.synthetic.main.activity_add_store.*
import java.io.ByteArrayOutputStream

//var globalObjectType = 0
    //var globalObjectName = ""
    var globalStoreName = ""
    var globalStoreType = ""
    var globalStorePhone = ""
    var globalAboutStore = ""
    var globalStoreImage : Bitmap? = null

class AddStoreActivity : AppCompatActivity() {

    var chosenImage : Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_store)
    }

    fun addStoreImageClick(view: View){

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
                addStoreImageButton.setImageBitmap(chosenImage)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    // CLICK TO ADD MAP



    fun addStoreMapClick(view: View) {

        val intent = Intent(applicationContext,MapsActivity::class.java)
        startActivity(intent)

    }

    // TIKLAYINCA DATABASE KAYIT

    fun addStoreSaveClick(view: View) {

        globalStoreName = addStoreNameText.text.toString()
        globalStoreType = addStoreTypeText.text.toString()
        globalStorePhone = addStorePhoneText.text.toString()
        globalAboutStore = addStoreAboutText.text.toString()
        globalStoreImage = chosenImage
        globalObjectType = 5
        globalObjectName = "Stores"


        if ( globalStoreImage == null) {
            Toast.makeText(applicationContext,"You Should Choose Image!", Toast.LENGTH_LONG).show()
        }else if ( globalStoreName == null || globalStoreName == "") {
            Toast.makeText(applicationContext,"You Should Write Name!", Toast.LENGTH_LONG).show()
        }else if ( globalStoreType == null || globalStoreType == "") {
            Toast.makeText(applicationContext,"You Should Write Type!", Toast.LENGTH_LONG).show()
        }else if ( globalStorePhone == null || globalStorePhone == "") {
            Toast.makeText(applicationContext,"You Should Write Phone!", Toast.LENGTH_LONG).show()
        }else if ( globalAboutStore == null || globalAboutStore == "") {
            Toast.makeText(applicationContext,"You Should Write About Store!", Toast.LENGTH_LONG).show()
        }else if ( latitude == null || latitude == "") {
            Toast.makeText(applicationContext,"You Should Choose a location!", Toast.LENGTH_LONG).show()
        }else if ( longitute == null || longitute == "") {
            Toast.makeText(applicationContext,"You Should Choose a location!", Toast.LENGTH_LONG).show()
        } else if ( globalObjectType == 5 && globalObjectName == "Stores" && loc == "selected") {
            val currentUser = ParseUser.getCurrentUser()
            val parseStores = ParseObject("Stores")
            parseStores.put("createdby",currentUser)
            parseStores.put("storeName",globalStoreName)
            parseStores.put("storeType",globalStoreType)
            parseStores.put("storePhone",globalStorePhone)
            parseStores.put("aboutStore",globalAboutStore)
            parseStores.put("latitude",latitude)
            parseStores.put("longitude",longitute)
            val byteArrayOutputStream = ByteArrayOutputStream()
            globalStoreImage!!.compress(Bitmap.CompressFormat.PNG,25,byteArrayOutputStream)
            val bytes = byteArrayOutputStream.toByteArray()
            val parseFile = ParseFile("image.png",bytes)
            parseStores.put("image",parseFile)
            parseStores.saveInBackground { e ->
                if (e != null) {
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Location Created", Toast.LENGTH_LONG).show()
                    val intent5 = Intent(applicationContext, StoresActivity::class.java)
                    startActivity(intent5)
                    finish()
                }
            }


        }



    }


}
