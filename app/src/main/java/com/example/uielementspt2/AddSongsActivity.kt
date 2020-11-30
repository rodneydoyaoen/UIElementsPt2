package com.example.uielementspt2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielementspt2.models.Song

class AddSongsActivity : AppCompatActivity() {
    lateinit var addSongBtn: Button
    lateinit var songTitleET :EditText
    lateinit var artistET: EditText
    lateinit var albumET: EditText
    lateinit var addBackBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_songs)

        val databaseHandler = SongsTableHandler(this)
        songTitleET= findViewById(R.id.songTitleET)
        artistET= findViewById(R.id.artistET)
        albumET= findViewById(R.id.albumET)

        addSongBtn = findViewById(R.id.addSongBtn)
        addBackBtn= findViewById(R.id.addBackBtn)

        addSongBtn.setOnClickListener{

            val title = songTitleET.text.toString()
            val artist = artistET.text.toString()
            val album = albumET.text.toString()
            val song = Song(title = title, artist = artist, album = album)
            if(databaseHandler.create(song)){
                Toast.makeText(applicationContext, "Song added.",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext, "Error, cannot add song.",Toast.LENGTH_SHORT).show()
            }
            clearFields()
        }
        addBackBtn.setOnClickListener{
            startActivity(Intent(applicationContext, MainActivity::class.java))
            adapter.notifyDataSetChanged()
        }
    }
    fun clearFields(){
        songTitleET.text.clear()
        artistET.text.clear()
        albumET.text.clear()
    }
}