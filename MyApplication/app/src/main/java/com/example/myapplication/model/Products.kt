package com.example.example

import com.google.gson.annotations.SerializedName


data class Products(

    @SerializedName("name") var name: String? = null,
    @SerializedName("tagline") var tagline: String? = null,
    @SerializedName("rating") var rating: Double? = null,
    @SerializedName("date") var date: String? = null

)