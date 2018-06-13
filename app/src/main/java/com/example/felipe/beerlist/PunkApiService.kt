package com.example.felipe.beerlist

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path


/**
 * Created by Felipe on 05/02/2018.
 */
interface PunkApiService {

    @GET("beers")
    fun getAll(): Observable<List<Beer>>

    @GET("beers/{beerID}")
    fun get(@Path("beerID") beerID: String): Observable<List<Beer>>

    /**
     * Companion object to create the PunkApiService
     */
    companion object Factory {
        fun create(): PunkApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.punkapi.com/v2/")
                    .build()

            return retrofit.create(PunkApiService::class.java)
        }
    }

}