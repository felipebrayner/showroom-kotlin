package com.example.felipe.beerlist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.felipe.beerlist.model.Beer
import com.example.felipe.beerlist.service.PunkApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashActivity : AppCompatActivity() {
    private val apiService = PunkApiService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apiService.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ b -> openMainActivity(b)}
    }

    private fun openMainActivity(beers: List<Beer>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("beers", ArrayList(beers))
        startActivity(intent)
        finish()
    }
}