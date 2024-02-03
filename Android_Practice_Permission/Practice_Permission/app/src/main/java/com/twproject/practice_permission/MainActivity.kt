package com.twproject.practice_permission

import android.Manifest
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.twproject.practice_permission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val requestSinglePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted: Boolean ->
            if (isGranted) {
                // 퍼미션을 허용했을때 action -> action 진행 or 워크플로우
            } else {

            }
        }

    val requestMultiPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perlist ->
            // 퍼미션을 진행 했을때 워크플로우
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goAlbumTv.setOnClickListener {

            // 함수 기본코드
            // verifyStoragePermissions(this)

            // 클래스로 permission check 관리
            // PermissionManager.verifyReadImageVideoPermissions(this)
            // PermissionManager.verifyCameraPermissions(this)
            // PermissionManager.verifyLocationPermissions(this)

            // requestSinglePermissionLauncher.launch(
            //     Manifest.permission.READ_MEDIA_IMAGES
            // )

            val permissionList = arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            requestMultiPermissionLauncher.launch(permissionList)
        }
    }

    // 권한이 있는지 확인하는 코드
    fun verifyStoragePermissions(activity: Activity) {
        var permission = 0

        permission = ActivityCompat.checkSelfPermission(
            activity,Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        Log.d("testPermission", permission.toString())
    }


}