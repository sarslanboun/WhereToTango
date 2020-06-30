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
import kotlinx.android.synthetic.main.activity_detail_store.*

class DetailStoreActivity : AppCompatActivity(), OnMapReadyCallback {

    var storeChosen = ""
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_store)


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapDetailStoreView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val intent = intent
        storeChosen = intent.getStringExtra("storeName")

    }


    override fun onMapReady(p0: GoogleMap) {
        mMap = p0


        val query = ParseQuery<ParseObject>("Stores")
        query.whereEqualTo("storeName",storeChosen)
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
                                detailStoreImageView.setImageBitmap(bitmap)
                                val storeName = parseObject.get("storeName") as String
                                val storeType = parseObject.get("storeType") as String
                                val storePhone = parseObject.get("storePhone") as String
                                val aboutStore = parseObject.get("aboutStore") as String
                                val latitude = parseObject.get("latitude") as String
                                val longitude = parseObject.get("longitude") as String

                                detailStoreNameView.text = storeName
                                detailStoreTypeView.text = storeType
                                detailStorePhoneView.text = storePhone
                                detailAboutStoreView.text = aboutStore


                                val latitudeDouble = latitude.toDouble()
                                val longitudeDouble = longitude.toDouble()

                                val chosenLocation = LatLng(latitudeDouble,longitudeDouble)
                                mMap.addMarker(MarkerOptions().position(chosenLocation).title(storeName))
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chosenLocation,17f))

                            }
                        }
                    }
                }

            }
        }



    }


}
