package com.example.finalproject.interfaces

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("results")
    val results: List<Product>
)
