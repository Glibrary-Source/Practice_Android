package com.twproject.practice_camerax

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.twproject.practice_camerax.databinding.ActivityCameraIntentBinding
import com.twproject.practice_camerax.utils.PermissionRequestManager

class CameraIntentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraIntentBinding
    private val permissionRequestManager = PermissionRequestManager()

    // Camera contracts
    private var cameraContracts = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.extras?.getParcelable("data", Bitmap::class.java)
            } else {
                result.data?.extras?.get("data") as Bitmap
            }
            binding.imgCamera.setImageBitmap(imageBitMap)
        }
    }

    // Video contracts
    private var videoContracts = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->

    }

    // Single permission check
    private val requestSinglePermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPermissionCheck.setOnClickListener {
            permissionRequestManager.onClickRequestPermission(this, requestSinglePermissionLauncher)
        }

        binding.btnCamera.setOnClickListener {
            dispatchTakePictureIntent()
//            dispatchTakeVideoIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            cameraContracts.launch(takePictureIntent)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    private fun dispatchTakeVideoIntent() {
        val takeVideoIntent = Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA)
        try {
            videoContracts.launch(takeVideoIntent)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }
}