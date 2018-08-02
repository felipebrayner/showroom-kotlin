package com.example.felipe.beerlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_beer_details.textview_beer_description
import kotlinx.android.synthetic.main.activity_beer_details.textview_beer_name
import kotlinx.android.synthetic.main.activity_beer_details.textview_beer_tagline

class BeerDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var beer = intent.extras["beer"] as Beer
        textview_beer_description.text = beer.description
        textview_beer_name.text = beer.name
        textview_beer_tagline.text = beer.tagline
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}