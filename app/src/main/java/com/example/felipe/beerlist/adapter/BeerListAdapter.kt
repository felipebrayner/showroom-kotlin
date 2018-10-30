package com.example.felipe.beerlist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import com.example.felipe.beerlist.model.Beer
import com.example.felipe.beerlist.R
import kotlinx.android.synthetic.main.beer_item.view.*

class BeerListAdapter(private var items: List<Beer>,
                      private val listener: OnItemClickListener) : RecyclerView.Adapter<BeerListAdapter.ViewHolder>() {

    private var allBeers: List<Beer> = emptyList()

    init {
        allBeers = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.beer_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val beer = items[position]
        viewHolder.bindView(beer, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                items = allBeers
                val list = ArrayList<Beer>()

                for (b in items) {
                    if (b.name!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        list.add(b)
                    }
                }

                val result = Filter.FilterResults()
                result.values = list
                return result
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                items = results.values as ArrayList<Beer>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(beer: Beer, listener: OnItemClickListener) {
            itemView.beer_name.text = beer.name
            itemView.beer_tagline.text = beer.tagline

            itemView.setOnClickListener { listener.onItemClick(beer) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(beer: Beer)
    }
}