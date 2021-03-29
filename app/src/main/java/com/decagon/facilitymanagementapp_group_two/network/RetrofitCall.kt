package com.decagon.facilitymanagementapp_group_two.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    val retrofitBuilder : Retrofit.Builder by lazy {
Retrofit.Builder().baseUrl("https://git-scm.com/").addConverterFactory(GsonConverterFactory.create())
    }

    val apiservice : Apiservice by lazy {
        retrofitBuilder.build().create(Apiservice::class.java)
    }
}