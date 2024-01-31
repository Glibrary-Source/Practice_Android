package com.twproject.practice_mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.twproject.practice_mvvm.model.GithubObject
import com.twproject.practice_mvvm.model.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubViewModel: ViewModel() {
    private val _getGithub = MutableLiveData<GithubUser>()
    val getGithub: LiveData<GithubUser> = _getGithub

    fun getGithubUser(userName: String) {
        GithubObject.githubService.getUser(userName).enqueue(object : Callback<GithubUser>{
            override fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
                if (response.isSuccessful) {
                    _getGithub.value = response.body()
                } else Log.d("TAG", "onResponse error: $response")
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                Log.d("TAG", "onFailure response fail: $call")
                Log.e("TAG", "onFailure response fail: ${t.printStackTrace()}", t.cause)
            }
        })
    }
}