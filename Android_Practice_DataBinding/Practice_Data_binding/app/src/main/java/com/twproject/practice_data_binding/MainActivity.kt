package com.twproject.practice_data_binding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.twproject.practice_data_binding.adapter.ProfileAdapter
import com.twproject.practice_data_binding.databinding.ActivityMainBinding
import com.twproject.practice_data_binding.model.ObservableData
import com.twproject.practice_data_binding.model.ProfileData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this@MainActivity

        setRcv()

        setObserv()
    }

    private fun setRcv() {
        val profileAdapter = ProfileAdapter(this)
        binding.mainRcv.layoutManager = LinearLayoutManager(this)
        binding.mainRcv.adapter = profileAdapter
        profileAdapter.data = listOf(
            ProfileData(profile = "https://plus.unsplash.com/premium_photo-1669324357471-e33e71e3f3d8?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", name = "Kang", age = 26),
            ProfileData(profile = "https://images.unsplash.com/photo-1624555130581-1d9cca783bc0?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", name = "Kim", age = 25)
        )
        profileAdapter.notifyDataSetChanged()
    }

    fun btnClick(view: View) {
        Toast.makeText(this, "Button Click", Toast.LENGTH_SHORT).show()
    }

    fun setObserv() {
        var item = ObservableData()
        item.site = "Naver"
        binding.site = item

        CoroutineScope(IO).launch {
            delay(3000)
            item.site = "Google"
        }
    }
}