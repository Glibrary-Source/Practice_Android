package com.twproject.practice_mvvm

import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    // ViewModel()을 상속받을 경우
    // class MainViewModel():ViewModel(){}

    //LiveData
    //값이 변경되는 경우 MutableLiveData로 선언한다.
    var count = MutableLiveData<Int>()

    init {
        count.value = 0
    }

    fun increase(view : View) {
        count.value = count.value?.plus(1)
    }

    fun decrease(view : View) {
        count.value = count.value?.minus(1)
    }


}