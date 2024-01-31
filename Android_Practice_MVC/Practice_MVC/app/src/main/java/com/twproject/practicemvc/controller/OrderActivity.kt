package com.twproject.practicemvc.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.twproject.practicemvc.R
import com.twproject.practicemvc.model.Americano
import com.twproject.practicemvc.model.CafeLatte
import com.twproject.practicemvc.model.TotalPrice

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val americanoModel = Americano()
        val cafeLatteModel = CafeLatte()
        val totalPriceModel = TotalPrice()

        val menuDeleteButton = findViewById<Button>(R.id.order_btn_americano_delete)
        val menuAddButton = findViewById<Button>(R.id.order_btn_americano_add)
        val menuCountText = findViewById<TextView>(R.id.order_text_count)
        val menuSelectAmericanoButton = findViewById<Button>(R.id.order_btn_menu_select_americano)
        val menuSelectCafeLatteButton = findViewById<Button>(R.id.order_btn_menu_select_cafelatte)
        val totalPriceText = findViewById<TextView>(R.id.order_text_total_price)
        val menuNameText = findViewById<TextView>(R.id.order_text_menu_name)

        var menuName = americanoModel.name

        // 메뉴 선택시 초기화 및 사용 모델 변경
        menuSelectAmericanoButton.setOnClickListener {
            americanoModel.resetBeverage()
            cafeLatteModel.resetBeverage()
            totalPriceModel.resetTotal()
            menuCountText.text = "0"
            totalPriceText.text = "합계 :"
            menuName = americanoModel.name
            menuNameText.text = menuName
        }

        menuSelectCafeLatteButton.setOnClickListener {
            americanoModel.resetBeverage()
            cafeLatteModel.resetBeverage()
            totalPriceModel.resetTotal()
            menuCountText.text = "0"
            totalPriceText.text = "합계 :"
            menuName = cafeLatteModel.name
            menuNameText.text = menuName
        }

        // menuName에 따라 사용 모델 변경
        menuDeleteButton.setOnClickListener {
            when(menuName){
                "아메리카노" -> {
                    americanoModel.delete()
                    menuCountText.text = "${americanoModel.quantity}"
                    totalPriceModel.decreaseTotalPrice(americanoModel.price)
                    totalPriceText.text = "합계 : ${totalPriceModel.totalPrice}"
                }
                "카페라떼" -> {
                    cafeLatteModel.delete()
                    menuCountText.text = "${cafeLatteModel.quantity}"
                    totalPriceModel.decreaseTotalPrice(cafeLatteModel.price)
                    totalPriceText.text = "합계 : ${totalPriceModel.totalPrice}"
                }
            }
        }

        menuAddButton.setOnClickListener {
            when(menuName){
                "아메리카노" -> {
                    americanoModel.add()
                    menuCountText.text = "${americanoModel.quantity}"
                    totalPriceModel.increaseTotalPrice(americanoModel.price)
                    totalPriceText.text = "합계 : ${totalPriceModel.totalPrice}"
                }
                "카페라떼" -> {
                    cafeLatteModel.add()
                    menuCountText.text = "${cafeLatteModel.quantity}"
                    totalPriceModel.increaseTotalPrice(cafeLatteModel.price)
                    totalPriceText.text = "합계 : ${totalPriceModel.totalPrice}"
                }
            }
        }
    }
}