package com.example.uielementspt2

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi

class AlbumDetailsActivity : AppCompatActivity() {

    lateinit var adapter : ArrayAdapter<String>
    lateinit var notificationManager: NotificationManager
    lateinit var songListView:ListView
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apss.notification"
    private val description = "Test notificiation"

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val albumTitle = findViewById<TextView>(R.id.albumTitle)
        val imgView = findViewById<ImageView>(R.id.imgView)

        val position: String? = intent.extras!!.getString("position")

        if (position.equals("+")) {
            albumTitle.text = position
            imgView.setImageResource(R.drawable.plus)
            getSong = arrayListOf("The A Team","Drunk","U.N.I", "Grade 8","Wake Me Up","Small bump","This",
                    "The City","Lego House","You Need Me, I Don't Need You","Kiss Me","Give Me Love")
            adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getSong)

        }
        else if(position.equals("x")){
            albumTitle.text = position
            imgView.setImageResource(R.drawable.x)
            getSong = arrayListOf("One","I'm a Mess","Sing", "Don't", "Nina", "Photograph",
                    "Bloodstream","Tenerife Sea","Runaway","The Man","Thinking Out Loud","Afire Love")
            adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getSong)

        }
        else if(position.equals("÷")) {
            albumTitle.text = position
            imgView.setImageResource(R.drawable.div)
            getSong = arrayListOf("Eraser", "Castle On The Hill", "Dive", "Shape of You", "Perfect", "Galway Girl", "Happier", "New Man",
                    "Hearts Don't Break Around Here", "What Do I Know", "How Would You Feel", "Supmermarket Flowers")
            adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getSong)

        }
        else if(position.equals(position)){
            val retSong: String? = intent.extras!!.getString("song").toString()
            //val album_position: String? = intent.extras!!.getString("album_position")
            albumTitle.text = position
            imgView.setImageResource(R.drawable.no_img)
            getSong.add(retSong.toString())
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, getSong)
            Log.i("check_values", "$getSong, $adapter")
        }

        songListView = findViewById<ListView>(R.id.songListView)
        songListView.adapter = adapter

        registerForContextMenu(songListView)
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.remove_song, menu)
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.remove_song ->  {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", { _ , _ ->
                            val intent = Intent(applicationContext , AlbumDetailsActivity::class.java)
                            val pendingIntent = PendingIntent.getActivity(
                                    applicationContext , 0 ,
                                    intent , PendingIntent.FLAG_UPDATE_CURRENT)
                            adapter.notifyDataSetChanged() // REMOVE SONG AFTER CLICKING YES
                        }).setNegativeButton("No", { dialog , _ ->
                            dialog.cancel()
                        })

                val alert = dialogBuilder.create()
                alert.setTitle("Notification Manager")
                alert.show()
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                getSong.removeAt(info.position)
                true
            }
            else -> super.onContextItemSelected(item)
        }

    }
}