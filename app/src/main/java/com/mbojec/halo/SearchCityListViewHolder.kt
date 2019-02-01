package com.mbojec.halo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchCityListViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.search_city_list_item, parent, false)){

    private val cityAddress = itemView.findViewById<TextView>(R.id.cityAddress)
    fun bindTo(feature: SearchCityList.Feature){
        cityAddress.text = feature.placeName
    }
}