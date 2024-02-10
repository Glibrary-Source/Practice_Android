package com.twproject.practice_restful_retrofit2.interfaces

import com.twproject.practice_restful_retrofit2.models.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

// header 없이
interface Api {
    @GET("movie/now_playing")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "2bace34658d74eeaf11d197e96276c91",
        @Query("page") page : Int,
        @Query("language") language : String = "ko,en-US"
    ): Call<GetMoviesResponse>
}

// header 추가
interface MovieApi {
    @GET("movie/now_playing")
    fun getPopularMovies(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") apiKey: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyYmFjZTM0NjU4ZDc0ZWVhZjExZDE5N2U5NjI3NmM5MSIsInN1YiI6IjY1YTBkNmM0ZjA0ZDAxMDEzMTc5MDU5ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.92h7Sy6FIwe6YKMFx_QaoBhNXyHrSLK2rzZ4jVaulPY",
        @Query("page") page : Int,
        @Query("language") language : String = "ko,en-US"
    ): Call<GetMoviesResponse>
}