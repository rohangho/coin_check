package com.example.coin.model

import com.google.gson.annotations.SerializedName

data class BaseCoinModel (


  @SerializedName("name") var name: String? = null,
  @SerializedName("symbol") var symbol: String? = null,
  @SerializedName("is_new") var isNew: Boolean? = null,
  @SerializedName("is_active") var isActive: Boolean? = null,
  @SerializedName("type") var type: String? = null
)