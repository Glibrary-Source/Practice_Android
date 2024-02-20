package com.twproject.practice_gemini

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.twproject.practice_gemini.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var prompt = ""
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setEditText()
        binding.textGemini.movementMethod = ScrollingMovementMethod()

        geminiTextOnly()
//        geminiChat()
//        streamChatOnlyText()
    }

    private fun setEditText() {
        binding.editChat.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                prompt = binding.editChat.text.toString()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    // text 전용
    private fun geminiTextOnly() {
        val generativeModel = GenerativeModel(
            // Use a model that's applicable for your use case (see "Implement basic use cases" below)
            modelName = "gemini-pro",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = BuildConfig.geminikey
        )

        binding.btnSendMsg.setOnClickListener {

            binding.textUser.text = prompt

            CoroutineScope(IO).launch {

                val response = generativeModel.generateContent(prompt)

                withContext(Main) {
                    binding.textGemini.text = response.text.toString()
                }
            }
            binding.editChat.text.clear()
        }
    }

    // image 분석
    private fun geminiImageView() {

        val generativeModel = GenerativeModel(
            // Use a model that's applicable for your use case (see "Implement basic use cases" below)
            modelName = "gemini-pro-vision",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = BuildConfig.geminikey
        )

        val image1 = BitmapFactory.decodeResource(this.resources, R.drawable.flower)
        val image2 = BitmapFactory.decodeResource(this.resources, R.drawable.flower)

        val inputContent = content {
            image(image1)
            image(image2)
            prompt
        }
        binding.btnSendMsg.setOnClickListener {

            binding.textUser.text = prompt

            CoroutineScope(IO).launch {
                val response = generativeModel.generateContent(inputContent)
                withContext(Main) {
                    binding.textGemini.text = response.text.toString()
                }
            }

            binding.editChat.text.clear()

        }
    }

    // 대화 형식(history)
    private fun geminiChat() {

        val generativeModel = GenerativeModel(
            // Use a model that's applicable for your use case (see "Implement basic use cases" below)
            modelName = "gemini-pro",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = BuildConfig.geminikey
        )

        val chat = generativeModel.startChat(
            history = listOf(

            )
        )

        binding.btnSendMsg.setOnClickListener {

            binding.textUser.text = prompt

            CoroutineScope(IO).launch {
                val response = chat.sendMessage(prompt)

                withContext(Main) {
                    try {
                        binding.textGemini.text = response.text.toString()
                    } catch (e: Exception) {
                        binding.textGemini.text = "나쁜말"
                    }
                }
            }
            binding.editChat.text.clear()
        }
    }

    // 스트리밍 채팅(이미지 포함) 사용
    private fun streamChatImage() {
        val generativeModel = GenerativeModel(
            // For text-and-image input (multimodal), use the gemini-pro-vision model
            modelName = "gemini-pro-vision",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = BuildConfig.geminikey
        )

        val image1 = BitmapFactory.decodeResource(this.resources, R.drawable.flower)
        val image2 = BitmapFactory.decodeResource(this.resources, R.drawable.flower)

        val inputContent = content {
            image(image1)
            image(image2)
            prompt
        }

        binding.btnSendMsg.setOnClickListener {

            CoroutineScope(IO).launch {

                var fullResponse = ""

                generativeModel.generateContentStream(inputContent).collect { chunk ->
                    withContext(Main) {
                        binding.textGemini.text = chunk.text
                    }

                    fullResponse += chunk.text
                }
            }
            binding.editChat.text.clear()
        }
    }

    // 스트리밍 채팅 온리 텍스트
    private fun streamChatOnlyText() {
        val generativeModel = GenerativeModel(
            // For text-and-image input (multimodal), use the gemini-pro-vision model
            modelName = "gemini-pro",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = BuildConfig.geminikey
        )

        val inputContentText = content {
            prompt
        }

        binding.btnSendMsg.setOnClickListener {

            CoroutineScope(IO).launch {
                var fullResponse = ""

                generativeModel.generateContentStream(inputContentText).collect { chunk ->
                    withContext(Main) {
                        binding.textGemini.text = chunk.text
                    }

                    fullResponse += chunk.text
                }
            }
            binding.editChat.text.clear()
        }
    }



}
