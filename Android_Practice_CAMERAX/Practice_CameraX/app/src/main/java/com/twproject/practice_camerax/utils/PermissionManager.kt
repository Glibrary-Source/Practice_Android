package com.twproject.practice_camerax.utils

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.core.content.ContextCompat

class PermissionManager {
    companion object {

        private const val PERMISSION_CAMERA = Manifest.permission.CAMERA
        private const val PERMISSION_RECORDE_AUDIO = Manifest.permission.ACCESS_COARSE_LOCATION
        private const val PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val PERMISSION_READ_MEDIA_IMAGES = Manifest.permission.READ_MEDIA_IMAGES
        private const val PERMISSION_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION

        fun verifyCameraPermissions(activity: Activity): Boolean {
            var permission = 0
            // check if we have camera permission
            permission = ContextCompat.checkSelfPermission(
                activity,
                PERMISSION_CAMERA
            )
            return permission == 0
        }

        fun verifyRecordAudioPermissions(activity: Activity): Boolean {
            var permission = 0
            // check if we have camera permission
            permission = ContextCompat.checkSelfPermission(
                activity,
                PERMISSION_RECORDE_AUDIO
            )
            return permission == 0
        }

        fun verifyStoragePermissions(activity: Activity): Boolean {
            var permission = 0
            // check if we have write permission
            permission = ContextCompat.checkSelfPermission(
                activity,
                PERMISSION_WRITE_STORAGE
            )
            return permission == 0
        }

        fun verifyReadImageVideoPermissions(activity: Activity): Boolean {
            var permission = 0
            // check if we have image & video permission
            permission = ContextCompat.checkSelfPermission(
                activity,
                PERMISSION_READ_MEDIA_IMAGES
            )
            return permission == 0
        }

        fun verifyLocationPermissions(activity: Activity): Boolean {
            var permission = 0
            // check if we have location permission
            permission = ContextCompat.checkSelfPermission(
                activity,
                PERMISSION_LOCATION
            )
            return permission == 0
        }
    }
}