package com.example.uielementspt2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import com.example.uielementspt2.models.Album
import com.google.android.material.snackbar.Snackbar

var modalList = ArrayList<Modal>()
var albumNames = arrayOf("+" , "x" , "÷")
var albumImages = intArrayOf(R.drawable.plus , R.drawable.x , R.drawable.div, R.drawable.no_img)
var getTitles: ArrayList<String> = ArrayList()

class AlbumActivity : AppCompatActivity() {
    /*var modalList = ArrayList<Modal>()
    var albumNames = arrayOf("+" , "x" , "÷")
    var albumImages = intArrayOf(R.drawable.plus , R.drawable.x , R.drawable.div, R.drawable.no_img)
    var getTitles: ArrayList<String> = ArrayList()*/
    lateinit var adapter: ArrayAdapter<String>
    lateinit var AlbumsTableHandler: AlbumsTableHandler
    lateinit var albums: MutableList<Album>
    lateinit var gridView: GridView
    lateinit var titles : MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        gridView = findViewById(R.id.gridView)

        AlbumsTableHandler = AlbumsTableHandler(this)
        albums = AlbumsTableHandler.read()
        titles = AlbumsTableHandler.title()

        for (x in albumNames) {
            getTitles.add(x)
        }
        for (album in titles) {
            getTitles.add(album)
        }
        for (x in albumImages.indices) {
            modalList.add(Modal(getTitles[x], albumImages[x]))
        }
        for (x in getTitles.indices) {
            if (x > 3) {
                modalList.add(Modal(getTitles[x], albumImages[3]))
            }
        }

        var adapter = CustomAdapter(modalList, this)
        gridView.adapter = adapter
        adapter.notifyDataSetChanged()

        registerForContextMenu(gridView)
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, id ->

            val intent = Intent(this, AlbumDetailsActivity::class.java)
            intent.putExtra("position", getTitles[position])
            startActivity(intent)
           }
        }
        //initialize context menu
        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            super.onCreateContextMenu(menu, v, menuInfo)
            val inflater: MenuInflater = menuInflater
            inflater.inflate(R.menu.edit_album, menu)
        }
        //initialize the menu instance
        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            val inflater = menuInflater
            inflater.inflate(R.menu.edit_album, menu)
            return true
        }
        override fun onContextItemSelected(item: MenuItem): Boolean {
            val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
            return when(item.itemId){
                R.id.edit_album ->  {
                    val album_id = albums[info.position-albumNames.size].id
                    val intent = Intent(applicationContext, EditAlbumActivity::class.java)
                    intent.putExtra("album_id",album_id)
                    startActivity(intent)
                    true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
