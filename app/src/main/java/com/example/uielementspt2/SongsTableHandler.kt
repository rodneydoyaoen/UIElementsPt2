package com.example.uielementspt2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.uielementspt2.models.Song

class SongsTableHandler (var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null , DATABASE_VERSION ){

    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "songs_database"
        private val TABLE_NAME = "songList"
        private val COL_ID = "id"
        private val COL_TITLE = "title"
        private val COL_ARTIST = "artist"
        private val COL_ALBUMS = "albums"
    }

    override fun onCreate(db: SQLiteDatabase?) { //CREATE
        //TODO("Not yet implemented")
        val query = "CREATE TABLE "+TABLE_NAME+ "("+ COL_ID+" INTEGER PRIMARY KEY, "+ COL_TITLE+" TEXT, "+ COL_ARTIST+" TEXT, "+COL_ALBUMS+" TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //TODO("Not yet implemented")
        db!!.execSQL("DROP TALBE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun create(songs: Song):Boolean{ //INSERT
        val database = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, songs.title)
        contentValues.put(COL_ARTIST, songs.artist)
        contentValues.put(COL_ALBUMS, songs.album)

        val result = database.insert(TABLE_NAME, null, contentValues)
        if(result == (0).toLong()){
            adapter.notifyDataSetChanged()
            return false
        }
        return true
    }
    fun update(songs: Song):Boolean {//UPDATE
        val database = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, songs.title)
        contentValues.put(COL_ARTIST, songs.artist)
        contentValues.put(COL_ALBUMS, songs.album)

        val result = database.update(TABLE_NAME, contentValues, "id ="+songs.id, null)
        if(result == 0){
            return false
        }
        return true
    }
    fun delete(song: Song): Boolean { // DELETE
        val database = this.writableDatabase
        val result = database.delete(TABLE_NAME, "id=${song.id}", null)
        if (result == 0) {
            return false
        }
        return true
    }
    fun read(): MutableList<Song>{ // SELECT
        val songList: MutableList<Song> = ArrayList<Song>()
        val query = "SELECT * FROM "+ TABLE_NAME
        val database = this.readableDatabase
        var cursor: Cursor?
        try{
            cursor = database.rawQuery(query, null)
        }catch (e: SQLException){
            return songList
        }

        var id: Int
        var title: String
        var artist: String
        var album: String

        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                artist = cursor.getString(cursor.getColumnIndex(COL_ARTIST))
                album = cursor.getString(cursor.getColumnIndex(COL_ALBUMS))
                val song = Song(id,title,artist,album)
                songList.add(song)
            } while (cursor.moveToNext())
        }
        return songList
    }
    fun title(): MutableList<String>{ // SELECT
        val songList: MutableList<String> = ArrayList<String>()
        val query = "SELECT * FROM " + TABLE_NAME
        val database = this.readableDatabase
        var cursor: Cursor?
        try{
            cursor = database.rawQuery(query, null)
        }catch (e: SQLException){
            return songList
        }
        var title: String
        if(cursor.moveToFirst()) {
            do {
                title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                songList.add(title)
            } while (cursor.moveToNext())
        }
        return songList
    }
    fun readOne(song_id: Int): Song{
        var oneSong = Song(0, "", "", "")
        val query = "SELECT * FROM $TABLE_NAME WHERE id=$song_id"
        val database = this.readableDatabase
        var cursor: Cursor?
        try{
            cursor = database.rawQuery(query, null)
        }catch (e: SQLException){
            return oneSong
        }

        var id: Int
        var title: String
        var artist: String
        var album: String

        if(cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(COL_ID))
            title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
            artist = cursor.getString(cursor.getColumnIndex(COL_ARTIST))
            album = cursor.getString(cursor.getColumnIndex(COL_ALBUMS))
            oneSong = Song(id,title,artist,album)
        }
        return oneSong
    }
}
