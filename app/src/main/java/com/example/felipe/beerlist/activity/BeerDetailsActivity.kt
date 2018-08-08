package com.example.felipe.beerlist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.felipe.beerlist.model.Beer
import com.example.felipe.beerlist.R
import com.vicpin.krealmextensions.save
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_beer_details.textview_beer_description
import kotlinx.android.synthetic.main.activity_beer_details.textview_beer_name
import kotlinx.android.synthetic.main.activity_beer_details.textview_beer_tagline
import kotlinx.android.synthetic.main.activity_beer_details.img_favorite_beer

class BeerDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val beer = intent.extras["beer"] as Beer
        textview_beer_description.text = beer.description
        textview_beer_name.text = beer.name
        textview_beer_tagline.text = beer.tagline

        val realm = Realm.getDefaultInstance()
        img_favorite_beer.setOnClickListener{
            var b = realm.where<Beer>().equalTo("id", beer.id).findFirst()
            if(b == null) {
                beer.save()
            } else {
                realm.beginTransaction()
                b.deleteFromRealm()
                realm.commitTransaction()
            }
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