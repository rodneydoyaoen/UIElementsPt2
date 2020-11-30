package com.example.uielementspt2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielementspt2.models.Album

class EditAlbumActivity : AppCompatActivity() {
    lateinit var editAlbumBtn: Button
    lateinit var editAlbumBackBtn: Button
    lateinit var editAlbumTitleET : EditText
    lateinit var editReleaseET: EditText
    lateinit var album: Album
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_album)

        var album_id = intent.getIntExtra("song_id", 0)

        val databaseHandler = AlbumsTableHandler(this)
        album = databaseHandler.readOne(album_id)

        editAlbumBtn = findViewById(R.id.editAlbumBtn)
        editAlbumBackBtn = findViewById(R.id.editAlbumBackBtn)

        editAlbumTitleET = findViewById(R.id.editAlbumTitleET)
        editReleaseET= findViewById(R.id.editReleaseET)

        editAlbumTitleET.setText((album.album_title))
        editReleaseET.setText((album.release))

        editAlbumBtn.setOnClickListener{
            val title = editAlbumTitleET.text.toString()
            val release = editReleaseET.text.toString()

            val updated_album = Album(
                id = album.id,
                album_title = title,
                release = release
            )
            if(databaseHandler.update(updated_album)){
                Toast.makeText(applicationContext, "Album updated.", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(applicationContext, "Error, try again.", Toast.LENGTH_SHORT).show()
            }
        }
        editAlbumBackBtn.setOnClickListener{
            startActivity(Intent(applicationContext, MainActivity::class.java))
            adapter.notifyDataSetChanged()
        }
    }
}