package com.example.customnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        const val CHANNEL_ID = "calls"
        const val NOTIFICATION_ID_C = 483
        const val NOTIFICATION_ID_STD = 482
    }
    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var remoteViews: RemoteViews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager = NotificationManagerCompat.from(applicationContext)
        remoteViews = RemoteViews(packageName, R.layout.notification_layout)
        toggleLayouts.setOnCheckedChangeListener { buttonView, isChecked ->
            remoteViews = if(isChecked){
                RemoteViews(packageName, R.layout.notification_layout_cl)
            }else{
                RemoteViews(packageName, R.layout.notification_layout)
            }
        }
        btnStdNotification.setOnClickListener { v -> getNotification() }
        btnCustomNotification.setOnClickListener { v -> getCustomNotification() }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun getNotification(){
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        createNotificationChannel()

        notificationManager.notify(NOTIFICATION_ID_STD, builder.build())

    }

    fun getCustomNotification(){
        val customNotification = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(remoteViews)
            .setAutoCancel(true)

        createNotificationChannel()
        notificationManager.notify(NOTIFICATION_ID_C, customNotification.build())
    }
}
