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
import kotlinx.android.synthetic.main.activity_festivals.*

class FestivalsActivity : AppCompatActivity() {

    var festivalsArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_festivals)
        getFestivalsData()

        festivalsListView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext, DetailFestivalActivity::class.java)
            intent.putExtra("festivalName", festivalsArray[i])
            startActivity(intent)
        }


    }


    // DATALARI LISTELEYIP ALMAK

    fun getFestivalsData(){

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,festivalsArray)
        festivalsListView.adapter = arrayAdapter

        val query = ParseQuery.getQuery<ParseObject>("Festivals")
        query.findInBackground { objects, e ->
            if (e != null) {
                Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (objects.size > 0) {
                    festivalsArray.clear()

                    for (parseObject in objects) {
                        val festivalName = parseObject.get("festivalName") as String
                        festivalsArray.add(festivalName)
                    }

                    arrayAdapter.notifyDataSetChanged()

                }
            }
        }

    }



    // MENU OLUSTU

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val currentUser = ParseUser.getCurrentUser()
        println(currentUser.get("usertype").toString().toInt())
        if( currentUser.get("usertype").toString().toInt() == 1){
            val menuInflater = menuInflater
            menuInflater.inflate(R.menu.add_festival,menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_festval_item) {
            val intent = Intent(this, AddFestivalActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
