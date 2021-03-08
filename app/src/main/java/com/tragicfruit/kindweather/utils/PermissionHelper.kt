package com.tragicfruit.kindweather.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object PermissionHelper {

    val FINE_LOCATION = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    val BACKGROUND_LOCATION = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        FINE_LOCATION + arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    } else {
        FINE_LOCATION
    }

    fun hasPermission(context: Context?, permission: String): Boolean {
        return context?.let {
            ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_GRANTED
        } ?: false
    }

    fun hasPermissions(context: Context?, vararg permissions: String) =
        permissions.all { hasPermission(context, it) }

    fun hasFineLocationPermission(context: Context?) =
        hasPermissions(context, *FINE_LOCATION)

    fun hasBackgroundLocationPermission(context: Context?) =
        hasPermissions(context, *BACKGROUND_LOCATION)
}
