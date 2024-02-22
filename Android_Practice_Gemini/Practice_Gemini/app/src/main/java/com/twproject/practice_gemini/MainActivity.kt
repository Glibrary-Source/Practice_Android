package com.twproject.practice_gemini

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import com.twproject.adapter.ChatListAdapter
import com.twproject.practice_gemini.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG = "Gemini"
class MainActivity : AppCompatActivity() {

    private var prompt = ""
    private lateinit var binding: ActivityMainBinding
    private lateinit var chatRcView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setEditText()
        binding.textGemini.movementMethod = ScrollingMovementMethod()

        chatRcView = binding.rcChat

//        geminiTextOnly()
//        geminiImageView()
//        geminiChat()
//        streamChatOnlyText()
//        limitOption()
        harmfulSetOption()

    }


    // edit text 설정
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

        val chatHistory = mutableListOf<List<String>>()

        binding.btnSendMsg.setOnClickListener {

            chatHistory.add(listOf("당신", prompt))
            chatRcView.adapter = ChatListAdapter(this@MainActivity, chatHistory)

            CoroutineScope(IO).launch {

                val response = generativeModel.generateContent(prompt)
                val responseText = response.text.toString()
                chatHistory.add(listOf("Gemini", responseText))

                withContext(Main) {
                    chatRcView.adapter = ChatListAdapter(this@MainActivity, chatHistory)
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
        val image2 = BitmapFactory.decodeResource(this.resources, R.drawable.flower2)


        val chatHistory = mutableListOf<List<String>>()

        binding.btnSendMsg.setOnClickListener {

            val inputContent = content {
                image(image1)
                image(image2)
                text(prompt)
            }

            chatHistory.add(listOf("당신", prompt))
            chatRcView.adapter = ChatListAdapter(this@MainActivity, chatHistory)

            CoroutineScope(IO).launch {
                val response = generativeModel.generateContent(inputContent)
                val responseText = response.text.toString()
                chatHistory.add(listOf("Gemini", responseText))

                withContext(Main) {
                    chatRcView.adapter = ChatListAdapter(this@MainActivity, chatHistory)
                }
            }

            binding.editChat.text.clear()
        }
    }

    private fun geminiChat() {
        val generativeModel = GenerativeModel(
            // Use a model that's applicable for your use case (see "Implement basic use cases" below)
            modelName = "gemini-pro",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = BuildConfig.geminikey
        )

        val chat = generativeModel.startChat(
            history = listOf(
                content(role = "user") { text("지금부터 너는 부동산 중개인이라고 생각하고 대화해줘") },
                content(role = "model") { text("알겠습니다.") },
            )
        )

        val chatHistory = mutableListOf<List<String>>()

        binding.btnSendMsg.setOnClickListener {

            chatHistory.add(listOf("당신", prompt))
            chatRcView.adapter = ChatListAdapter(this@MainActivity, chatHistory)

            CoroutineScope(IO).launch {
                val response = chat.sendMessage(prompt)
                val aiResponse = response.text ?: "no return"
                chatHistory.add(listOf("Gemini", aiResponse))

                withContext(Main) {
                    chatRcView.adapter = ChatListAdapter(this@MainActivity, chatHistory)
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
            text(prompt)
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

        val inputContentText = content { prompt }

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

    // 콘텐츠 생성 제어
    private fun limitOption() {

        val config = generationConfig {
            temperature = 0.45f
            topK = 16
            topP = 0.1f
            maxOutputTokens = 200
            stopSequences = listOf("red")
        }

        val generativeModel = GenerativeModel(
            // 모델은 알맞게 변경
            modelName = "gemini-pro-vision",
            apiKey = BuildConfig.geminikey,
            generationConfig = config,
            safetySettings = listOf(
                SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE)
            )
        )

        val image1 = BitmapFactory.decodeResource(this.resources, R.drawable.leonardo_dicaprio_2010)

        val chatHistory = mutableListOf<List<String>>()

        binding.btnSendMsg.setOnClickListener {

            val inputContent = content {
                image(image1)
                text(prompt)
            }

            chatHistory.add(listOf("당신", prompt))
            chatRcView.adapter = ChatListAdapter(this@MainActivity, chatHistory)

            CoroutineScope(IO).launch {
                val response = generativeModel.generateContent(inputContent)
                val responseText = response.text.toString()
                chatHistory.add(listOf("Gemini", responseText))

                withContext(Main) {
                    chatRcView.adapter = ChatListAdapter(this@MainActivity, chatHistory)
                }
            }

            binding.editChat.text.clear()
        }
    }

    // 위험물 판단
    private fun harmfulSetOption() {
        val config = generationConfig {
            temperature = 1f
            topK = 16
            topP = 0.1f
            stopSequences = listOf("red")
        }

        val generativeModel = GenerativeModel(
            // 모델은 알맞게 변경
            modelName = "gemini-pro",
            apiKey = BuildConfig.geminikey,
            generationConfig = config,
            safetySettings = listOf(
                SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE),
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE),
                SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE),
                SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE),
            )
        )

        val chatHistory = mutableListOf<List<String>>()

        binding.btnSendMsg.setOnClickListener {

            chatHistory.add(listOf("당신", prompt))
            chatRcView.adapter = ChatListAdapter(this@MainActivity, chatHistory)

            CoroutineScope(IO).launch {
                try{
                    val response = generativeModel.generateContent(prompt)
                    val responseText = response.text.toString()
                    chatHistory.add(listOf("Gemini", responseText))

                    withContext(Main) {
                        chatRcView.adapter = ChatListAdapter(this@MainActivity, chatHistory)
                    }
                } catch (e: Exception) {
                    Log.e("GeminiTest", e.message.toString())
                }
            }

            binding.editChat.text.clear()
        }
    }

}
