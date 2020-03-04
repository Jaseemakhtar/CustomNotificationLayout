package com.example.customnotification

import android.app.IntentService
import android.content.Intent
import android.util.Log
import android.widget.Toast

const val ACTION_ANSWER = "com.example.customnotification.action.ANSWER"
const val ACTION_DECLINE = "com.example.customnotification.action.DECLINE"
const val ACTION_DECLINE_N_TEXT = "com.example.customnotification.action.DECLINE_TEXT"
const val ACTION_REMIND_LATER = "com.example.customnotification.action.REMIND_LATER"


class MyIntentService : IntentService("MyIntentService") {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("serv", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_ANSWER -> {
                handleAction("Answering...")
            }
            ACTION_DECLINE -> {
                handleAction("Aborting...")
            }
            ACTION_DECLINE_N_TEXT -> {
                handleAction("Sending Text...")
            }
            ACTION_REMIND_LATER -> {
                handleAction("Will Remind You Later...")
            }
        }
    }

    private fun handleAction(param1: String) {
        Toast.makeText(this, param1, Toast.LENGTH_SHORT).show()
    }
}
