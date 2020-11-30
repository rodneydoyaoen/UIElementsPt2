package com.example.uielementspt2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.uielementspt2.models.Album
import com.example.uielementspt2.models.Song

@Suppress("UNREACHABLE_CODE")
class AlbumsTableHandler (var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null , DATABASE_VERSION ){
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "albums_database"
        private val TABLE_NAME = "albumsList"
        private val COL_ID = "id"
        private val COL_ALBUMTITLE= "album_title"
        private val COL_RD = "release_date"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        //TODO("Not yet implemented")
        //TODO("Not yet implemented")
        val query = "CREATE TABLE "+ TABLE_NAME + "("+ COL_ID +" INTEGER PRIMARY KEY, "+ COL_ALBUMTITLE +" TEXT, "+ COL_RD +" TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //TODO("Not yet implemented")
        db!!.execSQL("DROP TALBE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun create(albums: Album):Boolean{ //INSERT
        val database = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_ALBUMTITLE, albums.album_title)
        contentValues.put(COL_RD, albums.release)

        val result = database.insert(TABLE_NAME, null, contentValues)
        if(result == (0).toLong()){
            return false
        }
        return true
    }
    fun read(): MutableList<Album>{ // SELECT
        val albumList: MutableList<Album> = ArrayList<Album>()
        val query = "SELECT * FROM "+ TABLE_NAME
        val database = this.readableDatabase
        var cursor: Cursor?
        try{
            cursor = database.rawQuery(query, null)
        }catch (e: SQLException){
            return albumList
        }

        var id: Int
        var album_title: String
        var release: String

        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                album_title = cursor.getString(cursor.getColumnIndex(COL_ALBUMTITLE))
                release = cursor.getString(cursor.getColumnIndex(COL_RD))
                val albums = Album(id,album_title,release)
                albumList.add(albums)
            } while (cursor.moveToNext())
        }
        return albumList
    }
    fun title(): MutableList<String>{ // SELECT
        val albumList: MutableList<String> = ArrayList<String>()
        val query = "SELECT * FROM " + TABLE_NAME
        val database = this.readableDatabase
        var cursor: Cursor?
        try{
            cursor = database.rawQuery(query, null)
        }catch (e: SQLException){
            return albumList
        }
        var title: String
        if(cursor.moveToFirst()) {
            do {
                title = cursor.getString(cursor.getColumnIndex(COL_ALBUMTITLE))
                albumList.add(title)
            } while (cursor.moveToNext())
        }
        return albumList
    }
    fun readOne(album_id: Int): Album {
        var oneAlbum = Album(0, "", "")
        val query = "SELECT * FROM ${TABLE_NAME} WHERE id=$album_id"
        val database = this.readableDatabase
        var cursor: Cursor?
        try{
            cursor = database.rawQuery(query, null)
        }catch (e: SQLException){
            return oneAlbum
        }

        var id: Int
        var album_title: String
        var release: String

        if(cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(COL_ID))
            album_title = cursor.getString(cursor.getColumnIndex(COL_ALBUMTITLE))
            release = cursor.getString(cursor.getColumnIndex(COL_RD))
            oneAlbum = Album(id,album_title,release)
        }
        return oneAlbum
    }
      fun update(albums: Album):Boolean {//UPDATE
            val database = this.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(COL_ALBUMTITLE, albums.album_title)
            contentValues.put(COL_RD, albums.release)

            val result = database.update(TABLE_NAME, contentValues, "id ="+albums.id, null)
            if(result == 0){
                return false
            }
            return true
      }
}