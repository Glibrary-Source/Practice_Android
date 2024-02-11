package com.twproject.practice_restful_retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.twproject.practice_restful_retrofit2.databinding.ActivityRestapiBinding
import com.twproject.practice_restful_retrofit2.interfaces.Api
import com.twproject.practice_restful_retrofit2.interfaces.MovieApi
import com.twproject.practice_restful_retrofit2.models.GetMoviesResponse

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestapiBinding

    private lateinit var api: Api
    private lateinit var movieApi: MovieApi

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestapiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcTest.layoutManager = LinearLayoutManager(this)

        // api key 로 request
        api = retrofit.create(Api::class.java)

        // token 으로 request
        movieApi = retrofit.create(MovieApi::class.java)

        binding.btnGetData.setOnClickListener {
            getPopularMoviesApiKey(1)
            getPopularMoviesToken(1)
        }

    }

    fun getPopularMoviesApiKey(page: Int = 1) {
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            Log.d("Repository", "Movies: ${responseBody.movies}")
                        } else {
                            Log.d("Repository", "Failed to get response")
                        }
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }

    private fun getPopularMoviesToken(page: Int = 1) {
        movieApi.getPopularMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            Log.d("Repository", "Movies: ${responseBody.movies}")
                        } else {
                            Log.d("Repository", "Failed to get response")
                        }
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }

}