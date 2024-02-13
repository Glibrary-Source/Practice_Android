package com.twproject.practice_uiux.view.mainpage

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.twproject.practice_uiux.R
import com.twproject.practice_uiux.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNav()
//        adjust()

    }

    // bottom navigationbar 이동 조정
    private fun setBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navigationBottomNav.setupWithNavController(navController)
    }

    // view margin 코드내 조정
//    private fun setViewMargin() {
//        val layoutParams = binding.includedTopNav.ctTopNav.layoutParams as ConstraintLayout.LayoutParams
//        val newMarginInPixel = getStatusBarHeight()
//        layoutParams.setMargins(0, newMarginInPixel, 0, 0)
//        binding.includedTopNav.ctTopNav.layoutParams = layoutParams
//    }

    // Navigation Bar를 투명하게 만들었을때 윗쪽에있는 레이아웃이 시스템 상태바에 겹처질때 계산해서 마진값을 부여함
//    private fun getStatusBarHeight(): Int {
//        var result = 0
//
//        // Status bar height
//        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
//        if (resourceId > 0) {
//            result += resources.getDimensionPixelSize(resourceId)
//        }
//
//        return result
//    }

    // 몰입형 모드 변환 코드
//    private fun adjust() {
//        val windowInsetsController =
//            WindowCompat.getInsetsController(window, window.decorView)
//        windowInsetsController.systemBarsBehavior =
//            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//
//        window.decorView.setOnApplyWindowInsetsListener { view, windowInsets ->
//            if(windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
//                || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())) {
//                binding.includedTopNav.btnMainTopNavInfo.setOnClickListener {
//                    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
//                }
//            } else {
//                binding.includedTopNav.btnMainTopNavInfo.setOnClickListener {
//                    windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
//                }
//            }
//            view.onApplyWindowInsets(windowInsets)
//        }
//    }

}