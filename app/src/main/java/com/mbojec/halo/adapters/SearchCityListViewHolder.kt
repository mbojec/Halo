package com.mbojec.halo.adapters

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import com.mbojec.halo.model.SearchCityList
import com.mbojec.halo.ui.SearchFragmentDirections

class SearchCityListViewHolder(parent: ViewGroup, application: HaloApplication): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.search_city_list_item, parent, false)){

    private val cityAddress = itemView.findViewById<TextView>(R.id.cityAddress)
    private lateinit var feature: SearchCityList.Feature

    init {
        itemView.setOnClickListener {
            feature.geometry?.coordinates?.get(0)?.let { longitude ->
                application.networkRepository.fetchCityData(
                    longitude,
                    feature.geometry!!.coordinates!![1],
                    false
                )
            }
            val cityId = feature.feature_id?.split(".")?.get(1)
            val action = SearchFragmentDirections.actionSearchDestToMainDest(cityId!!.toLong())
            val handler = Handler()
            val task = Runnable {
                it.findNavController().navigate(action)
            }
            handler.postDelayed(task, 1000)
        }
    }

    fun bindTo(feature: SearchCityList.Feature?){
        feature?.let {
            this.feature = it
            cityAddress.text = feature.placeName
        }

    }
}