package com.twproject.practicemvc.model

class TotalPrice {

    var totalPrice = 0

    fun increaseTotalPrice(price: Int) {
        totalPrice += price
    }

    fun decreaseTotalPrice(price: Int) {
        totalPrice -= price
        if (totalPrice < 0) {
            totalPrice = 0
        }
    }

    fun resetTotal() {
        totalPrice = 0
    }

}