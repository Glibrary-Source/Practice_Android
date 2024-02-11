package com.twproject.practice_uiux.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.twproject.practice_uiux.R

class DetailPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.noStateTheme)
        setContentView(R.layout.activity_detail_page)
    }
}