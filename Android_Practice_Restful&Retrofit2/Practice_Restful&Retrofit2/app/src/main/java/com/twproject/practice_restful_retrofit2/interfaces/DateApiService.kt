package com.twproject.practice_restful_retrofit2.interfaces

import com.twproject.practice_restful_retrofit2.BuildConfig
import com.twproject.practice_restful_retrofit2.models.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

// header 없이
interface Api {
    @GET("movie/now_playing")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.MyKey,
        @Query("page") page : Int,
        @Query("language") language : String = "ko,en-US"
    ): Call<GetMoviesResponse>
}

// header 추가
interface MovieApi {
    @GET("movie/now_playing")
    fun getPopularMovies(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.MyToken}",
        @Query("page") page : Int,
        @Query("language") language : String = "ko,en-US"
    ): Call<GetMoviesResponse>
}