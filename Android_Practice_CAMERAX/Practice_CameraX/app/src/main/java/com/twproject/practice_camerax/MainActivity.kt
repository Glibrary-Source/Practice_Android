package com.twproject.practice_camerax

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val REQUEST_PERMISSIONS = 1

    private var cameraContracts = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.d("test_camera", result.data.toString())
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_camera).setOnClickListener {
            dispatchTakePictureIntent()
        }

        checkPermission()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            cameraContracts.launch(takePictureIntent)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    private fun checkPermission() {
        var permission = mutableMapOf<String, String>()
        permission["camera"] = Manifest.permission.CAMERA
        permission["storageRead"] = Manifest.permission.READ_EXTERNAL_STORAGE
        permission["storeageWrite"] = Manifest.permission.WRITE_EXTERNAL_STORAGE

        var denied = permission.count {
            ContextCompat.checkSelfPermission(
                this,
                it.value
            ) == PackageManager.PERMISSION_DENIED
        }
        if (denied > 0) {
            requestPermissions( permission.values.toTypedArray(), REQUEST_PERMISSIONS )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {

            /* 1. 권한 확인이 다 끝난 후 동의하지 않은것이 있다면 종료
            var count = grantResults.count { it == PackageManager.PERMISSION_DENIED } // 동의 안한 권한의 개수
            if(count != 0) {
            Toast.makeText(applicationContext, "서비스의 필요한 권한입니다.\n권한에 동의해주세요.", Toast.LENGTH_SHORT).show()
            finish()
            } */

            /* 2. 권한 요청을 거부했다면 안내 메시지 보여주며 앱 종료 */
            grantResults.forEach {
                if(it == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(applicationContext, "서비스의 필요한 권합입니다. 권한에 동의해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}