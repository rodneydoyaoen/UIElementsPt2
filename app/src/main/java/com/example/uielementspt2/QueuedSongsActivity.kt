package com.example.uielementspt2

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.remove
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar

class QueuedSongsActivity : AppCompatActivity() {
    lateinit var adapter : ArrayAdapter<String>
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queued_songs)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, getSong)
        val queListView = findViewById<ListView>(R.id.queListView)

        queListView.adapter = adapter
        registerForContextMenu(queListView)

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
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                getSong.removeAt(info.position)
                adapter.notifyDataSetChanged()
                if(adapter.isEmpty){

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notificationChannel = NotificationChannel(
                                channelId , description , NotificationManager.IMPORTANCE_HIGH)
                        notificationChannel.enableLights(true)
                        notificationChannel.lightColor = Color.BLUE
                        notificationChannel.enableVibration(true)
                        notificationManager.createNotificationChannel(notificationChannel)

                        builder = Notification.Builder(this , channelId)
                                .setContentTitle("Song Queue Empty")
                                .setContentText("Your queue of songs is empty.")
                                .setSmallIcon(R.drawable.ic_launcher_background)

                    }else{
                        builder = Notification.Builder(this)
                                .setContentTitle("Song Queue Empty")
                                .setContentText("Your queue of songs is empty.")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                    }
                    notificationManager.notify(1234, builder.build())
                }
                val toast:Toast = Toast.makeText(applicationContext, "Song removed from queue.", Toast.LENGTH_LONG)
                toast.show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}