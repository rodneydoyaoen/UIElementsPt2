package com.example.uielementspt2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

var getSong = arrayListOf<String>()
val albumList: Array<String> = arrayOf("The A Team","Drunk","U.N.I", "Grade 8","Wake Me Up","Small bump","This",
        "The City","Lego House","You Need Me, I Don't Need You","Kiss Me","Give Me Love",
        "One","I'm a Mess","Sing", "Don't", "Nina", "Photograph", "Bloodstream",
        "Tenerife Sea","Runaway","The Man","Thinking Out Loud","Afire Love",
        "Eraser","Castle On The Hill", "Dive", "Shape of You","Perfect", "Galway Girl", "Happier", "New Man",
        "Hearts Don't Break Around Here","What Do I Know", "How Would You Feel", "Supmermarket Flowers")


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val songList: ListView = findViewById<ListView>(R.id.songList)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,albumList)
        songList.adapter = adapter
        registerForContextMenu(songList)
        }
        //initialize context menu
        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            super.onCreateContextMenu(menu, v, menuInfo)
            val inflater: MenuInflater = menuInflater
            inflater.inflate(R.menu.song_option_menu, menu)
        }
        //initialize the menu instance
        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            val inflater = menuInflater
            inflater.inflate(R.menu.main_menu, menu)
            return true
        }
        //context menu for clicked item in listview
        override fun onContextItemSelected(item: MenuItem): Boolean {
            return when(item.itemId){
                R.id.add_to_queue ->  {
                    val songList: ListView = findViewById<ListView>(R.id.songList)
                    val snackbar:Snackbar = Snackbar.make(songList, "Song added to queue. Preview in Queue Activity?", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Yes", View.OnClickListener {
                        startActivity(Intent(applicationContext, QueuedSongsActivity::class.java))
                    })
                    snackbar.show()
                    //Toast.makeText(this, "Added to Queue", Toast.LENGTH_LONG).show()
                    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                    getSong.add(albumList[info.position])
                    true
                }
                else -> super.onContextItemSelected(item)
            }
        }
        //songs menu
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when(item.itemId){
                R.id.show_que -> {
                    startActivity(Intent(this,QueuedSongsActivity::class.java))
                    true
                }
                R.id.show_albums -> {
                    startActivity(Intent(this, AlbumActivity::class.java))
                    true
                }
                else -> return super.onOptionsItemSelected(item)
            }
    }
}