package com.tragicfruit.kindweather.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionHelper {

    val FULL_LOCATION = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)

    fun hasPermission(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    fun hasPermissions(context: Context, vararg permissions: String) =
        permissions.all { hasPermission(context, it) }

    fun hasFullLocationPermission(context: Context) = hasPermissions(context, *FULL_LOCATION)

}