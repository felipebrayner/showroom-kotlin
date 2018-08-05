package com.example.felipe.beerlist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.SearchView
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.listview

class MainActivity : AppCompatActivity() {

    private val apiService = PunkApiService.create()
    private lateinit var adapter: BeerListAdapter
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent.extras == null) {
            isFavorite = false
            apiService.getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{ b -> showBeers(b)}
        } else {
            isFavorite = true
        }
    }

    override fun onStart() {
        super.onStart()

        if(isFavorite) {
            val realm = Realm.getDefaultInstance()
            var beers = arrayListOf<Beer>()
            realm.where<Beer>().findAll().forEach {b ->
                beers.add(realm.copyFromRealm<Beer>(b))
            }
            showBeers(beers)
        }
    }

    private fun showBeers(beers: List<Beer>) {
        adapter = BeerListAdapter(this, beers)
        listview.adapter = adapter
        adapter.notifyDataSetChanged()

        listview.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            val it = Intent(this, BeerDetailsActivity::class.java)
            it.putExtra("beer", beers[position])
            startActivity(it)
        }
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
            menu!!.findItem(R.id.fav).setVisible(false)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.fav) {
            val it = Intent(this, MainActivity::class.java)
            it.putExtra("favorite", true)
            startActivity(it)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}