package com.twproject.practice_camerax

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.core.app.ActivityCompat

class PermissionManager {
    companion object {
        private val TAG = "PERMISSION_CHECK"

        private const val PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val PERMISSION_CAMERA = Manifest.permission.CAMERA
        private const val PERMISSION_READ_MEDIA_IMAGES = Manifest.permission.READ_MEDIA_IMAGES
        private const val PERMISSION_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION

        fun verifyStoragePermissions(activity: Activity): Boolean {
            var permission = 0
            // check if we have write permission
            permission = ActivityCompat.checkSelfPermission(
                activity,
                PERMISSION_WRITE_STORAGE
            )
            Log.d(TAG,"Storage permission : $permission")
            return permission == 0
        }

        fun verifyCameraPermissions(activity: Activity): Boolean {
            var permission = 0
            // check if we have camera permission
            permission = ActivityCompat.checkSelfPermission(
                activity,
                PERMISSION_CAMERA
            )
            Log.d(TAG,"Camera permission : $permission")
            return permission == 0
        }

        fun verifyReadImageVideoPermissions(activity: Activity): Boolean {
            var permission = 0
            // check if we have image & video permission
            permission = ActivityCompat.checkSelfPermission(
                activity,
                PERMISSION_READ_MEDIA_IMAGES
            )
            Log.d(TAG,"ReadImage&Video permission : $permission")
            return permission == 0
        }

        fun verifyLocationPermissions(activity: Activity): Boolean {
            var permission = 0
            // check if we have location permission
            permission = ActivityCompat.checkSelfPermission(
                activity,
                PERMISSION_LOCATION
            )
            Log.d(TAG,"Location permission : $permission")
            return permission == 0
        }
    }
}