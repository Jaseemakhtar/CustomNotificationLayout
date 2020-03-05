package com.example.customnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
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
        const val ACTION_ANSWER = "com.example.customnotification.action.ANSWER"
        const val ACTION_DECLINE = "com.example.customnotification.action.DECLINE"
        const val ACTION_DECLINE_N_TEXT = "com.example.customnotification.action.DECLINE_TEXT"
        const val ACTION_REMIND_LATER = "com.example.customnotification.action.REMIND_LATER"
    }
    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var remoteViews: RemoteViews
    private var notificationTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager = NotificationManagerCompat.from(applicationContext)
        remoteViews = RemoteViews(packageName, R.layout.notification_layout)
        toggleLayouts.setOnCheckedChangeListener { buttonView, isChecked ->
            remoteViews = if(isChecked){
                toggleLayouts.text = getString(R.string.constraint_layout)
                RemoteViews(packageName, R.layout.notification_layout_cl)
            }else{
                toggleLayouts.text = getString(R.string.relative_layout)
                RemoteViews(packageName, R.layout.notification_layout)
            }
        }
        btnStdNotification.setOnClickListener {
            notificationTime = System.currentTimeMillis()
            getNotification()
        }
        btnCustomNotification.setOnClickListener {
            notificationTime = System.currentTimeMillis()
            /*startService(Intent(this@MainActivity, MyIntentService::class.java))*/
            getCustomNotification()
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            notificationManager.createNotificationChannel(NotificationChannel(
                CHANNEL_ID,
                name,
                importance
            ).apply {
                setSound(null, null)
                description = descriptionText
            })
        }
    }


    fun getNotification(){
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setWhen(notificationTime)
        createNotificationChannel()

        notificationManager.notify(NOTIFICATION_ID_STD, builder.build())

    }

    fun getCustomNotification(){

        val callAnswer = Intent(this, MyBroadcastReceiver::class.java)
        callAnswer.action = ACTION_ANSWER
        remoteViews.setOnClickPendingIntent(R.id.btnAnswer, PendingIntent.getBroadcast(this, 0, callAnswer, 0))

        val callDecline = Intent(this, MyBroadcastReceiver::class.java)
        callDecline.action = ACTION_DECLINE
        remoteViews.setOnClickPendingIntent(R.id.btnDecline, PendingIntent.getBroadcast(this, 0, callDecline, PendingIntent.FLAG_UPDATE_CURRENT))

        val callText = Intent(this, MyBroadcastReceiver::class.java)
        callText.action = ACTION_DECLINE_N_TEXT
        remoteViews.setOnClickPendingIntent(R.id.btnReply, PendingIntent.getBroadcast(this, 0, callText, PendingIntent.FLAG_UPDATE_CURRENT))

        val callRemind = Intent(this, MyBroadcastReceiver::class.java)
        callRemind.action = ACTION_REMIND_LATER
        remoteViews.setOnClickPendingIntent(R.id.btnReminder, PendingIntent.getBroadcast(this, 0, callRemind, PendingIntent.FLAG_UPDATE_CURRENT))

        val customNotification = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(remoteViews)
            .setAutoCancel(true)
            .setWhen(notificationTime)
        createNotificationChannel()
        notificationManager.notify(NOTIFICATION_ID_C, customNotification.build())
    }
}
