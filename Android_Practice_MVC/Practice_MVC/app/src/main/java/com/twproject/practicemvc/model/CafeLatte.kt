package com.twproject.practicemvc.model

class CafeLatte: Beverage(2500, "카페라떼") {
    override fun add() {
        ++quantity
    }

    override fun delete() {
        --quantity
        if (quantity < 0) {
            quantity = 0
        }
    }

    override fun resetBeverage() {
        quantity = 0
    }
}