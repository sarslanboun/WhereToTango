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
import kotlinx.android.synthetic.main.activity_stores.*

class StoresActivity : AppCompatActivity() {

    var storesArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stores)
        getStoresData()

        storesListView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext, DetailStoreActivity::class.java)
            intent.putExtra("storeName", storesArray[i])
            startActivity(intent)
        }


    }


    // DATALARI LISTELEYIP ALMAK

    fun getStoresData(){

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,storesArray)
        storesListView.adapter = arrayAdapter

        val query = ParseQuery.getQuery<ParseObject>("Stores")
        query.findInBackground { objects, e ->
            if (e != null) {
                Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (objects.size > 0) {
                    storesArray.clear()

                    for (parseObject in objects) {
                        val storeName = parseObject.get("storeName") as String
                        storesArray.add(storeName)
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
            menuInflater.inflate(R.menu.add_store,menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_store_item) {
            val intent = Intent(this, AddStoreActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}
