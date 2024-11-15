package com.example.coin.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BaseCoinModel (

  @SerialName("name"      ) var name     : String?  = null,
  @SerialName("symbol"    ) var symbol   : String?  = null,
  @SerialName("is_new"    ) var isNew    : Boolean? = null,
  @SerialName("is_active" ) var isActive : Boolean? = null,
  @SerialName("type"      ) var type     : String?  = null

)