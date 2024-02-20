package com.fermion.android.cats_facts.network

import com.fermion.android.base.network.BaseDataRepository
import com.fermion.android.base.network.NetworkResult
import com.fermion.example.cat_fact.ui.facts.models.CatFactModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatFactRepository @Inject constructor(private val catFactService: CatFactService) :
    BaseDataRepository(catFactService) {

    suspend fun getFact(): NetworkResult<CatFactModel> {
        return getNetworkResult {
            catFactService.getFact()
        }
    }

}