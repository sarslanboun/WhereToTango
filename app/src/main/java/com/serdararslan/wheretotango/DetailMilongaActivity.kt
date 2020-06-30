package com.serdararslan.wheretotango

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_detail_milonga.*

class DetailMilongaActivity : AppCompatActivity() , OnMapReadyCallback {

    var milongaChosen = ""
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_milonga)


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapDetailMilongaView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val intent = intent
        milongaChosen = intent.getStringExtra("milongaName")

    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0


        val query = ParseQuery<ParseObject>("Milongas")
        query.whereEqualTo("milongaName",milongaChosen)
        query.findInBackground { objects, e ->
            if (e != null) {
                Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {

                if (objects.size > 0) {
                    for (parseObject in objects) {
                        val image = parseObject.get("image") as ParseFile
                        image.getDataInBackground { data, e ->
                            if (e!=null) {
                                Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()

                            } else {

                                val bitmap = BitmapFactory.decodeByteArray(data,0,data.size)
                                detailMilongaImageView.setImageBitmap(bitmap)
                                val milongaName = parseObject.get("milongaName") as String
                                val milongaPrice = parseObject.get("milongaPrice") as String
                                val milongaPhone = parseObject.get("milongaPhone") as String
                                val milongaAbout = parseObject.get("milongaAbout") as String
                                val latitude = parseObject.get("latitude") as String
                                val longitude = parseObject.get("longitude") as String

                                detailMilongaNameView.text = milongaName
                                detailMilongaPriceView.text = milongaPrice
                                detailMilongaPhoneView.text = milongaPhone
                                detailAboutMilongaView.text = milongaAbout


                                val latitudeDouble = latitude.toDouble()
                                val longitudeDouble = longitude.toDouble()

                                val chosenLocation = LatLng(latitudeDouble,longitudeDouble)
                                mMap.addMarker(MarkerOptions().position(chosenLocation).title(milongaName))
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chosenLocation,17f))

                            }
                        }
                    }
                }

            }
        }



    }


}
