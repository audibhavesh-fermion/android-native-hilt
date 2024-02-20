package com.fermion.example.cat_fact.ui.facts.models


import com.google.gson.annotations.SerializedName

class CatFactModel(
    @SerializedName("fact")
    val fact: String,
    @SerializedName("length")
    val length: Int
)