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
import kotlinx.android.synthetic.main.activity_lessons.*

class LessonsActivity : AppCompatActivity() {

    var lessonsArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)
        getLessonsData()

        lessonsListView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext, DetailLessonActivity::class.java)
            intent.putExtra("lessonName", lessonsArray[i])
            startActivity(intent)
        }
    }

    // DATALARI LISTELEYIP ALMAK

    fun getLessonsData(){

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,lessonsArray)
        lessonsListView.adapter = arrayAdapter

        val query = ParseQuery.getQuery<ParseObject>("Lessons")
        query.findInBackground { objects, e ->
            if (e != null) {
                Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (objects.size > 0) {
                    lessonsArray.clear()

                    for (parseObject in objects) {
                        val lessonName = parseObject.get("lessonName") as String
                        lessonsArray.add(lessonName)
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
            menuInflater.inflate(R.menu.add_lesson,menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_lesson_item) {
            val intent = Intent(this, AddLessonActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
