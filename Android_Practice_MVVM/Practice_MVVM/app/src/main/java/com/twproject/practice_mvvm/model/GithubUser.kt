package com.twproject.practice_mvvm.model

import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("login") val username: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("company") val company: String,
    @SerializedName("blog") val blog: String,
    @SerializedName("location") val location: String,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int
)