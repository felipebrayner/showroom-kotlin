package com.example.felipe.beerlist.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.felipe.beerlist.model.Beer
import com.example.felipe.beerlist.R

class BeerListAdapter(private var activity: Activity, private var items: List<Beer>) : BaseAdapter() {

    private var allBeers: List<Beer> = emptyList()

    init {
        allBeers = items
    }

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var txtTagline: TextView? = null

        init {
            this.txtName = row?.findViewById(R.id.beer_name)
            this.txtTagline = row?.findViewById(R.id.beer_tagline)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.beer_item, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var beer = items[position]
        viewHolder.txtName?.text = beer.name
        viewHolder.txtTagline?.text = beer.tagline

        return view as View
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

    override fun getItem(i: Int): Beer {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}