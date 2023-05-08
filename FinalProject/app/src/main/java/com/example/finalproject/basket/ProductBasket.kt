package com.example.finalproject.basket

val basket = arrayListOf<ProductBasket>()

data class ProductBasket(val id: Int, val name: String, val description: String, val image: Int, val price: Double)
