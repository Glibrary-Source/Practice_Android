package com.twproject.practice_mvvm.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.twproject.practice_mvvm.R
import com.twproject.practice_mvvm.databinding.ActivityUserInfoBinding
import com.twproject.practice_mvvm.viewmodel.GithubViewModel

class UserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserInfoBinding
    private lateinit var viewModel: GithubViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)

        viewModel = ViewModelProvider(this)[GithubViewModel::class.java]

        val name = intent.getStringExtra("name")

        if (name != null) {
            viewModel.getGithubUser(name)
            viewModel.getGithub.observe(this) {data ->
                binding.userNameText.text = "${data.username}(${data.name})"
                binding.followInfo.text = "followers: ${data.followers}, following: ${data.following}"
                binding.companyText.text = data.company
                binding.locationText.text = data.location
                binding.linkText.text = data.blog
            }
        }
    }
}