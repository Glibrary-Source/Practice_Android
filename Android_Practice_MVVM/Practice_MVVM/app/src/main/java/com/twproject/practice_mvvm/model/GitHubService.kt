package com.twproject.practice_mvvm.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("user/{owner}")
    fun getUser(
        @Path("owner") owner: String
    ): Call<GithubUser>
}