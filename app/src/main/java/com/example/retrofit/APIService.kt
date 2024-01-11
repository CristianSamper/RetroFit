package com.example.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getDigi(@Url url:String): Response<digiResponse>
    @GET
    suspend fun getDigibyImg(@Url url:String): Response<List<digiImg>>
}