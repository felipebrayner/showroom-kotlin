package com.example.felipe.beerlist.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.example.felipe.beerlist.model.Beer
import com.example.felipe.beerlist.adapter.BeerListAdapter
import com.example.felipe.beerlist.R
import com.example.felipe.beerlist.service.PunkApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.recyclerview

private const val IS_FAVORITE = "favorite"

class MainActivity : AppCompatActivity(), BeerListAdapter.OnItemClickListener {

    private val apiService = PunkApiService.create()
    private lateinit var adapter: BeerListAdapter
    private var currentPage = 1
    private var isDownloading = false
    private var isFavorite = false
    private var noMoreBeers = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent.extras.getBoolean(IS_FAVORITE, false)) {
            isFavorite = true
        } else {
            initListView(intent.extras.get("beers") as ArrayList<Beer>)
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
            initListView(beers)
        }
    }

    private fun initListView(beers: ArrayList<Beer>) {
        adapter = BeerListAdapter (beers, this)
        recyclerview.adapter = adapter
        recyclerview.setHasFixedSize(true)

        if(isFavorite) {
            return
        }

        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                var lManager = recyclerview.layoutManager as LinearLayoutManager
                if(lManager.findLastVisibleItemPosition() == lManager.itemCount - 1) {
                    if(!isDownloading || noMoreBeers) {
                        isDownloading = true
                        currentPage++
                        downloadBeers(currentPage)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun downloadBeers(page: Int = 1) {
        apiService.getPerPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ b -> onDownloadComplete(b as ArrayList<Beer>) }
    }

    private fun onDownloadComplete(beers: ArrayList<Beer>) {
        if(beers.isEmpty()) {
            noMoreBeers = true
            isDownloading = false
            return
        }

        adapter.addMoreBeers(beers)
        isDownloading = false
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