package com.example.felipe.beerlist.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.example.felipe.beerlist.model.Beer
import com.example.felipe.beerlist.adapter.BeerListAdapter
import com.example.felipe.beerlist.R
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.recyclerview

private const val IS_FAVORITE = "favorite"

class MainActivity : AppCompatActivity(), BeerListAdapter.OnItemClickListener {
    private lateinit var adapter: BeerListAdapter
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent.extras.getBoolean(IS_FAVORITE, false)) {
            isFavorite = true
        } else {
            showBeers(intent.extras.get("beers") as List<Beer>)
        }
    }

    override fun onStart() {
        super.onStart()

        if(isFavorite) {
            val realm = Realm.getDefaultInstance()
            var beers = arrayListOf<Beer>()
            realm.where<Beer>().findAll().forEach { b ->
                beers.add(realm.copyFromRealm<Beer>(b))
            }
            showBeers(beers)
        }
    }

    private fun showBeers(beers: List<Beer>) {
        adapter = BeerListAdapter (beers, this)
        recyclerview.adapter = adapter
        recyclerview.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchView = menu!!.findItem(R.id.search).actionView as SearchView
        /*val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))*/
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(text: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.getFilter().filter(newText)
                return false
            }
        })

        if(isFavorite) {
            menu!!.findItem(R.id.fav).isVisible = false
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.fav) {
            val it = Intent(this, MainActivity::class.java)
            it.putExtra(IS_FAVORITE, true)
            startActivity(it)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(beer: Beer) {
        val it = Intent(this, BeerDetailsActivity::class.java)
        it.putExtra("beer", beer)
        startActivity(it)
    }
}