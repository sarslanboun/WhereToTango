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
import kotlinx.android.synthetic.main.activity_detail_lesson.*

class DetailLessonActivity : AppCompatActivity(), OnMapReadyCallback {

    var lessonChosen = ""
    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lesson)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapDetailLessonView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val intent = intent
        lessonChosen = intent.getStringExtra("lessonName")
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0


        val query = ParseQuery<ParseObject>("Lessons")
        query.whereEqualTo("lessonName",lessonChosen)
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
                                detailLessonImageView.setImageBitmap(bitmap)
                                val lessonName = parseObject.get("lessonName") as String
                                val lessonInstructors = parseObject.get("lessonInstructors") as String
                                val lessonPhone = parseObject.get("lessonPhone") as String
                                val aboutLesson = parseObject.get("aboutLesson") as String
                                val latitude = parseObject.get("latitude") as String
                                val longitude = parseObject.get("longitude") as String

                                detailLessonNameView.text = lessonName
                                detailLessonInstructorsView.text = lessonInstructors
                                detailLessonPhoneView.text = lessonPhone
                                detailAboutLectureView.text = aboutLesson


                                val latitudeDouble = latitude.toDouble()
                                val longitudeDouble = longitude.toDouble()

                                val chosenLocation = LatLng(latitudeDouble,longitudeDouble)
                                mMap.addMarker(MarkerOptions().position(chosenLocation).title(lessonName))
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chosenLocation,17f))

                            }
                        }
                    }
                }

            }
        }



    }

}
