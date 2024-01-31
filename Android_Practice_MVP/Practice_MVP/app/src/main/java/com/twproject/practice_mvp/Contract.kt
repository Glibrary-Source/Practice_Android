package com.twproject.practice_mvp

import org.json.JSONObject

interface Contract {

    interface View {
        fun showInfo(info: JSONObject)
    }

    interface Presenter {
        fun initInfo()
        fun setInfo(info: JSONObject)
        fun saveInfo(info: JSONObject)
    }
}