package com.example.felipe.beerlist.service

import com.example.felipe.beerlist.model.Beer
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path
import retrofit2.http.Query

interface PunkApiService {

    @GET("beers")
    fun getPerPage(@Query("page") page: Int = 1, @Query("per_page") perPage: Int = 50): Observable<List<Beer>>

    @GET("beers/{beerID}")
    fun get(@Path("beerID") beerID: String): Observable<List<Beer>>

    /**
     * Companion object to create the PunkApiService
     */
    companion object Factory {
        val URL = "https://api.punkapi.com/v2/"
        fun create(): PunkApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL)
                    .build()

            return retrofit.create(PunkApiService::class.java)
        }
    }
}