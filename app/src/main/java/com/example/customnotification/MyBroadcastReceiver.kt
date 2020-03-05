package com.example.customnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.customnotification.MainActivity.Companion.ACTION_ANSWER
import com.example.customnotification.MainActivity.Companion.ACTION_DECLINE
import com.example.customnotification.MainActivity.Companion.ACTION_DECLINE_N_TEXT
import com.example.customnotification.MainActivity.Companion.ACTION_REMIND_LATER

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_ANSWER -> {
                handleAction(context,"Answering...")
            }
            ACTION_DECLINE -> {
                handleAction(context, "Aborting...")
            }
            ACTION_DECLINE_N_TEXT -> {
                handleAction(context, "Sending Text...")
            }
            ACTION_REMIND_LATER -> {
                handleAction(context, "Will Remind You Later...")
            }
        }

    }

    private fun handleAction(context: Context?, param1: String) {
        Toast.makeText(context, param1, Toast.LENGTH_SHORT).show()
        Log.d("jtest", param1)
    }
}