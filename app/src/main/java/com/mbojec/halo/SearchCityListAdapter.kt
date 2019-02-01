package com.mbojec.halo

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SearchCityListAdapter(searchCityList: SearchCityList): RecyclerView.Adapter<SearchCityListViewHolder>(){
    private var list: SearchCityList? = null

    init {
        list = searchCityList
    }

    override fun getItemCount(): Int {
        return list?.features?.size?: 0
    }

    override fun onBindViewHolder(holder: SearchCityListViewHolder, position: Int) {
        list?.features?.get(position)?.let { holder.bindTo(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCityListViewHolder = SearchCityListViewHolder(parent)

    fun updateList(newSearchCityList: SearchCityList){
        list = newSearchCityList
    }

    fun clearList(){
        list = null
    }

}