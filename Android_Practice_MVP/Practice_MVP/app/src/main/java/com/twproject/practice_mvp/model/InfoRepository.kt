package com.twproject.practice_mvp.model

import android.content.Context
import org.json.JSONObject

// InfoRepository.kt

class InfoRepository(context: Context) : InfoDataSource {
    private val infoLocalDataSource = InfoLocalDataSource(context)

    override fun getInfo(callback: InfoDataSource.LoadInfoCallback) {
        infoLocalDataSource.getInfo(callback)
    }

    override fun saveInfo(info: JSONObject) {
        infoLocalDataSource.saveInfo(info)
    }
}