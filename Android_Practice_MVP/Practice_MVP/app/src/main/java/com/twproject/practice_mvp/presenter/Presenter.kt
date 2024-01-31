package com.twproject.practice_mvp.presenter

import com.twproject.practice_mvp.Contract
import com.twproject.practice_mvp.model.InfoDataSource
import com.twproject.practice_mvp.model.InfoRepository
import org.json.JSONObject

// Presenter.kt

class Presenter(
    val view: Contract.View,
    val repository: InfoRepository
) : Contract.Presenter {

    override fun initInfo() {
        repository.getInfo(object: InfoDataSource.LoadInfoCallback {
            override fun onInfoLoaded(info: JSONObject) {
                view.showInfo(info)
            }
            override fun onDataNotAvailable() {
                // Nothing
            }
        })
    }

    override fun setInfo(info: JSONObject) {
        view.showInfo(info)
    }

    override fun saveInfo(info: JSONObject) {
        repository.saveInfo(info)
    }
}