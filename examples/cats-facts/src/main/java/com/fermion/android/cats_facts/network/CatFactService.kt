package com.fermion.android.cats_facts.network

import com.fermion.android.base.network.BaseApiService
import com.fermion.example.cat_fact.ui.facts.models.CatFactModel
import retrofit2.Response
import retrofit2.http.GET

interface CatFactService : BaseApiService {

    @GET("fact")
    suspend fun getFact(): Response<CatFactModel>
}