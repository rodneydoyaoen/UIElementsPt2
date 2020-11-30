package com.example.uielementspt2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import com.example.uielementspt2.models.Song
import com.google.android.material.snackbar.Snackbar

var getSong = arrayListOf<String>()
var songsList: ArrayList<String> = ArrayList()
var songArray = arrayOf(
        "The A Team",
        "Drunk",
        "U.N.I",
        "Grade 8",
        "Wake Me Up",
        "Small bump",
        "This",
        "The City",
        "Lego House",
        "You Need Me",
        "I Don't Need You",
        "Kiss Me",
        "Give Me Love",
        "One",
        "I'm a Mess",
        "Sing",
        "Don't",
        "Nina",
        "Photograph",
        "Bloodstream",
        "Tenerife Sea",
        "Runaway",
        "The Man",
        "Thinking Out Loud",
        "Afire Love",
        "Eraser",
        "Castle On The Hill",
        "Dive",
        "Shape of You",
        "Perfect",
        "Galway Girl",
        "Happier",
        "New Man",
        "Hearts Don't Break Around Here",
        "What Do I Know",
        "How Would You Feel",
        "Supermarket Flowers")

lateinit var adapter: ArrayAdapter<String>

class MainActivity : AppCompatActivity() {

    lateinit var songsTableHandler: SongsTableHandler
    lateinit var songs: MutableList<Song>
    lateinit var songList : ListView
    lateinit var titles : MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // albumList.addAll(array)
        songsTableHandler = SongsTableHandler(this)
        songs =  songsTableHandler.read()
        titles = songsTableHandler.title()

        songList = findViewById(R.id.songList)

        for (song in songArray){
            songsList.add(song)
        }
        for (song in titles){
            songsList.add(song)

        }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songsList)
        songList.adapter = adapter
        adapter.notifyDataSetChanged()

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
            val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
            return when(item.itemId){
                R.id.add_to_queue ->  {
                    val songList: ListView = findViewById<ListView>(R.id.songList)
                    val snackbar:Snackbar = Snackbar.make(songList, "Song added to queue. Preview in Queue Activity?", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Yes", View.OnClickListener {
                        startActivity(Intent(applicationContext, QueuedSongsActivity::class.java))
                    })
                    snackbar.show()
                    getSong.add(songsList[info.position])
                    true
                }
                R.id.edit_song -> {
                    val song_id = songs[info.position-songArray.size].id
                    val intent = Intent(applicationContext, EditSongsActivity::class.java)
                    intent.putExtra("song_id",song_id)
                    startActivity(intent)
                    true
                }
                R.id.delete_song -> {
                    val song = songs[info.position-37]
                    if(songsTableHandler.delete(song)){
                        songsList.removeAt(info.position)
                        adapter.notifyDataSetChanged()
                        Toast.makeText(applicationContext, "Song deleted", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(applicationContext, "Can't delete song, try again.", Toast.LENGTH_LONG).show()
                    }
                    true
                }
                R.id.add_to_album -> {
                    val intent = Intent(applicationContext, AlbumOptionsActivity::class.java)
                    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                    var w: String = (songsList[info.position])
                    intent.putExtra("song", w)
                    startActivity(intent)
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
                R.id.add_songs -> {
                    startActivity(Intent(this, AddSongsActivity::class.java))
                    true
                }
                R.id.add_album -> {
                    startActivity(Intent(this, AddAlbumActivity::class.java))
                    true
                }
                else -> return super.onOptionsItemSelected(item)
        }
    }
}



