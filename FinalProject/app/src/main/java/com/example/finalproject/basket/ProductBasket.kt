package com.example.finalproject.basket

val basket = arrayListOf<ProductBasket>()

data class ProductBasket(val id: Int, val name: String, val description: String, val price: Double, val image: Int, var count: Int = 1)
