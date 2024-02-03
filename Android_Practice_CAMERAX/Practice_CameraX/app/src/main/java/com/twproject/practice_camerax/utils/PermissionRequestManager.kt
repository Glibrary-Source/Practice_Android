package com.twproject.practice_camerax.utils

import android.Manifest
import android.app.Activity
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getString
import com.twproject.practice_camerax.R

class PermissionRequestManager {

    fun onClickRequestPermission(
        activity: Activity,
        requestSinglePermissionLauncher: ActivityResultLauncher<String>
    ) {
        when {

            // 퍼미션이 이미 있는지 확인
            PermissionManager.verifyCameraPermissions(activity) -> {
                Toast.makeText(activity, getString(activity, R.string.permission_granted), Toast.LENGTH_SHORT).show()
            }

            // 사용자가 명시적으로 퍼미션을 거부한 경우 처리 예) 유저가 퍼미션 deny 클릭
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.CAMERA
            ) -> {
                Toast.makeText(activity, getString(activity, R.string.permission_required), Toast.LENGTH_SHORT).show()
            }

            // 위에 두가지 경우가 아니라면 퍼미션 요청을 수행하는 코드
            else -> {
                requestSinglePermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

}