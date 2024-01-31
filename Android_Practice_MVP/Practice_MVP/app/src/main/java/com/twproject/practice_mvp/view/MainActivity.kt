package com.twproject.practice_mvp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.twproject.practice_mvp.Contract
import com.twproject.practice_mvp.databinding.ActivityMainBinding
import com.twproject.practice_mvp.model.InfoRepository
import com.twproject.practice_mvp.presenter.Presenter
import org.json.JSONObject

class MainActivity : AppCompatActivity(), Contract.View {
    private lateinit var presenter: Presenter
    private lateinit var repository: InfoRepository
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        repository = InfoRepository(this)
        presenter = Presenter(this@MainActivity, repository)

        presenter.initInfo()
        initButtonListener()
    }

    override fun showInfo(info: JSONObject) {
        binding.nameOutput.text = info.getString("name")
        binding.emailOutput.text = info.getString("email")
    }

    fun initButtonListener() {
        binding.buttonSubmit.setOnClickListener {

            var info = JSONObject()
            info.put("name", binding.nameInput.text.toString())
            info.put("email", binding.emailInput.text.toString())

            binding.nameInput.text.clear()
            binding.emailInput.text.clear()

            presenter.setInfo(info)
            presenter.saveInfo(info)
        }
    }
}