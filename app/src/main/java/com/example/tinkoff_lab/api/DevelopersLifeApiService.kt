package com.example.tinkoff_lab.api

import retrofit2.Call;
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET;

private const val BASE_URL = "https://developerslife.ru/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

public interface DevelopersLifeApiService {
    @GET("random?json=true")
    suspend fun getRandomPost(): Post
}

object DevelopersLifeApi {
    val retrofitService : DevelopersLifeApiService by lazy {
        retrofit.create(DevelopersLifeApiService::class.java)
    }
}