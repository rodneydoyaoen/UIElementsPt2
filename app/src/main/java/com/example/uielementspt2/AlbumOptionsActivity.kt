package com.example.uielementspt2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.uielementspt2.models.Album


class AlbumOptionsActivity : AppCompatActivity() {
    var modalList = ArrayList<Modal>()
    var albumNames = arrayOf("+" , "x" , "÷")
    var albumImages = intArrayOf(R.drawable.plus , R.drawable.x , R.drawable.div, R.drawable.no_img)
    var getTitles: ArrayList<String> = ArrayList()

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
        albums =  AlbumsTableHandler.read()
        titles = AlbumsTableHandler.title()

        for(x in albumNames){
            getTitles.add(x)
        }
        for(album in titles) {
            getTitles.add(album)
        }
        for(x in albumImages.indices){
            modalList.add(Modal(getTitles[x], albumImages[x]))
        }
        for(x in getTitles.indices){
            if(x>3){
                modalList.add(Modal(getTitles[x], albumImages[3]))
            }
        }

        var adapter = CustomAdapter(modalList, this)
        gridView.adapter = adapter
        adapter.notifyDataSetChanged()

        val pos: String = intent.extras!!.getString("song").toString()

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, id ->

            val intent = Intent(this, AlbumDetailsActivity::class.java)
            intent.putExtra("position", getTitles[id.toInt()])
            intent.putExtra("position", getTitles[position])
            intent.putExtra("song",pos)
            startActivity(intent)
        }
    }
}
