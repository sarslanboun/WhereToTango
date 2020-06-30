package com.serdararslan.wheretotango

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_schools.*

class SchoolsActivity : AppCompatActivity() {

    var schoolsArray = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schools)
        getSchoolsData()

        schoolsListView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext, DetailSchoolActivity::class.java)
            intent.putExtra("schoolName",schoolsArray[i])
            startActivity(intent)
        }

    }

        // DATALARI LISTELEYIP ALMAK

    fun getSchoolsData(){

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,schoolsArray)
        schoolsListView.adapter = arrayAdapter

        val query = ParseQuery.getQuery<ParseObject>("Schools")
        query.findInBackground { objects, e ->
            if (e != null) {
                Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_LONG).show()
            } else {
                if (objects.size > 0) {
                    schoolsArray.clear()

                    for (parseObject in objects) {
                        val schoolName = parseObject.get("schoolName") as String
                        schoolsArray.add(schoolName)
                    }

                    arrayAdapter.notifyDataSetChanged()

                }
            }
        }

    }


            //MENU OLUSTU

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val currentUser = ParseUser.getCurrentUser()
        println(currentUser.get("usertype").toString().toInt())
        if( currentUser.get("usertype").toString().toInt() == 1){
            val menuInflater = menuInflater
            menuInflater.inflate(R.menu.add_school,menu)
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_school_item) {
            val intent = Intent(this, AddSchoolActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

            // - - -



}
