package com.twproject.practicemvc.model

open class Beverage(price: Int, menuName: String) {
    var name = menuName
    var price = price
    var quantity = 0

    open fun add() {}
    open fun delete() {}
    open fun resetBeverage() {}
}