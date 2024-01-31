package com.twproject.practicemvc.model

class Americano : Beverage(1500, "아메리카노") {

    override fun add() {
        ++quantity
    }

    override fun delete() {
        --quantity
        if (quantity < 0){
            quantity = 0
        }
    }

    override fun resetBeverage() {
        quantity = 0
    }
}