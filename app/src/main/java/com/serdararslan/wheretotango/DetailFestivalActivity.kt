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
import kotlinx.android.synthetic.main.activity_detail_festival.*

class DetailFestivalActivity : AppCompatActivity()  , OnMapReadyCallback {

    var festivalChosen = ""
    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_festival)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapDetailFestivalView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val intent = intent
        festivalChosen = intent.getStringExtra("festivalName")
    }


    override fun onMapReady(p0: GoogleMap) {
        mMap = p0


        val query = ParseQuery<ParseObject>("Festivals")
        query.whereEqualTo("festivalName",festivalChosen)
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
                                detailFestivalImageView.setImageBitmap(bitmap)
                                val festivalName = parseObject.get("festivalName") as String
                                val festivalCity = parseObject.get("festivalCity") as String
                                val festivalStartDate = parseObject.get("festivalStartDate") as String
                                val festivalEndDate = parseObject.get("festivalEndDate") as String
                                val festivalPhone = parseObject.get("festivalPhone") as String
                                val festivalAbout = parseObject.get("festivalAbout") as String
                                val latitude = parseObject.get("latitude") as String
                                val longitude = parseObject.get("longitude") as String

                                detailFestivalNameView.text = festivalName
                                detailFestivalCityView.text = festivalCity
                                detailFestivalStartDateView.text = festivalStartDate
                                detailFestivalEndDateView.text = festivalEndDate
                                detailFestivalPhoneView.text = festivalPhone
                                detailAboutFestivalView.text = festivalAbout


                                val latitudeDouble = latitude.toDouble()
                                val longitudeDouble = longitude.toDouble()

                                val chosenLocation = LatLng(latitudeDouble,longitudeDouble)
                                mMap.addMarker(MarkerOptions().position(chosenLocation).title(festivalName))
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chosenLocation,17f))

                            }
                        }
                    }
                }

            }
        }



    }
}
