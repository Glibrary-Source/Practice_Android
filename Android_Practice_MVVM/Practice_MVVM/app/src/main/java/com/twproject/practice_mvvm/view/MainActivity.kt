package com.twproject.practice_mvvm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.twproject.practice_mvvm.R
import com.twproject.practice_mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.resultBtn.setOnClickListener {
            startActivity(Intent(this, UserInfoActivity::class.java)
                .putExtra("name", binding.githubUserName.text.toString()))
        }
    }
}