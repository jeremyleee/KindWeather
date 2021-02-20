package com.tragicfruit.kindweather.utils.controllers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var alertController: AlertController

    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            alertController.scheduleDailyAlert(context)
        }
    }
}
