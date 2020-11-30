package com.example.uielementspt2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielementspt2.models.Album

class AddAlbumActivity : AppCompatActivity() {
    lateinit var albumTitleET: EditText
    lateinit var releaseET: EditText
    lateinit var addAlbumBtn: Button
    lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_album)

        val databaseHandler = AlbumsTableHandler(this)
        albumTitleET= findViewById(R.id.albumTitleET)
        releaseET= findViewById(R.id.releaseET)

        addAlbumBtn = findViewById(R.id.addAlbumBtn)
        backBtn = findViewById(R.id.backBtn)

        addAlbumBtn.setOnClickListener{

            val album_title = albumTitleET.text.toString()
            val release = releaseET.text.toString()
            val album = Album(
                album_title = album_title,
                release = release
            )
            if(databaseHandler.create(album)){
                Toast.makeText(applicationContext, "Album added.", Toast.LENGTH_SHORT).show()
            }
            clearFields()
        }
        backBtn.setOnClickListener{
            startActivity(Intent(applicationContext, AlbumActivity::class.java))
            adapter.notifyDataSetChanged()
        }
    }
    fun clearFields(){
        albumTitleET.text.clear()
        releaseET.text.clear()
    }
}