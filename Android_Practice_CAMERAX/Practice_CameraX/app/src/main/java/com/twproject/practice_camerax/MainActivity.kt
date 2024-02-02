package com.twproject.practice_camerax

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.twproject.practice_camerax.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var layout: View
    private lateinit var binding: ActivityMainBinding

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
            findViewById<ImageView>(R.id.img_camera).setImageBitmap(imageBitMap)
        }
    }

    private val requestPermissionLauncher =
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layout = binding.mainLayout

        binding.btnCamera.setOnClickListener {
//            dispatchTakePictureIntent()
            onClickRequestPermission(layout)
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


    fun onClickRequestPermission(view: View) {
        when {
            // 퍼미션 체크를 하는 코드
            PermissionManager.verifyCameraPermissions(this) -> {
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show()
            }

            // 사용자가 명시적으로 퍼미션을 거부한 경우 처리하는 코드
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> {
                Toast.makeText(this, getString(R.string.permission_required), Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

}