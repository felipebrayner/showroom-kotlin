package com.example.felipe.beerlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_beer_details.textview_beer_description
import kotlinx.android.synthetic.main.activity_beer_details.textview_beer_name
import kotlinx.android.synthetic.main.activity_beer_details.textview_beer_tagline
import kotlinx.android.synthetic.main.activity_beer_details.img_favorite_beer

class BeerDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_details)

        val realm = Realm.getDefaultInstance()

        realm.where(SavedBeer::class.java).findAll().forEach{beer ->
            Toast.makeText(this, "Beer: " + beer.id, Toast.LENGTH_LONG).show()
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val beer = intent.extras["beer"] as Beer
        textview_beer_description.text = beer.description
        textview_beer_name.text = beer.name
        textview_beer_tagline.text = beer.tagline

        img_favorite_beer.setOnClickListener{
            Toast.makeText(this, "Salvando Beer: " + beer.name, Toast.LENGTH_LONG).show()
            realm.beginTransaction()
            realm.createObject(SavedBeer::class.java, beer.id)
            realm.commitTransaction()
        }
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