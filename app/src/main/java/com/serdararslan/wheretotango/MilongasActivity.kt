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
import kotlinx.android.synthetic.main.activity_milongas.*

class MilongasActivity : AppCompatActivity() {

    var milongasArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_milongas)
        getMilongasData()

        milongasListView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext, DetailMilongaActivity::class.java)
            intent.putExtra("milongaName", milongasArray[i])
            startActivity(intent)
        }


    }


    fun getMilongasData(){

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,milongasArray)
        milongasListView.adapter = arrayAdapter

        val query = ParseQuery.getQuery<ParseObject>("Milongas")
        query.findInBackground { objects, e ->
            if (e != null) {
                Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (objects.size > 0) {
                    milongasArray.clear()

                    for (parseObject in objects) {
                        val milongaName = parseObject.get("milongaName") as String
                        milongasArray.add(milongaName)
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
            menuInflater.inflate(R.menu.add_milonga,menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_milonga_item) {
            val intent = Intent(this, AddMilongaActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
