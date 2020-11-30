package com.example.uielementspt2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielementspt2.models.Song

class EditSongsActivity : AppCompatActivity() {
    lateinit var editSongBtn: Button
    lateinit var editBackBtn: Button
    lateinit var editTitleET : EditText
    lateinit var editArtistET: EditText
    lateinit var editAlbumET: EditText
    lateinit var song:Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_songs)

        var song_id = intent.getIntExtra("song_id", 0)

        val databaseHandler = SongsTableHandler(this)
        song = databaseHandler.readOne(song_id)

        editTitleET = findViewById(R.id.editTitleET)
        editArtistET = findViewById(R.id.editArtistET)
        editAlbumET = findViewById(R.id.editAlbumET)
        editSongBtn = findViewById(R.id.editSongBtn)
        editBackBtn= findViewById(R.id.editBackBtn)

        editTitleET.setText((song.title))
        editArtistET.setText((song.artist))
        editAlbumET.setText((song.album))

        editSongBtn.setOnClickListener{
            val title = editTitleET.text.toString()
            val artist = editArtistET.text.toString()
            val album = editAlbumET.text.toString()

            val updated_song = Song(id = song.id, title = title, artist = artist, album = album)
            if(databaseHandler.update(updated_song)){
                Toast.makeText(applicationContext, "Song updated.", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(applicationContext, "Error, try again.", Toast.LENGTH_SHORT).show()
            }
        }
        editBackBtn.setOnClickListener{
            startActivity(Intent(applicationContext, MainActivity::class.java))
            adapter.notifyDataSetChanged()
        }
    }
}