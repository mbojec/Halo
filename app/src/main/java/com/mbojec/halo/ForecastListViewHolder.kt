package com.mbojec.halo

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.database.ForecastEntity
import com.mbojec.halo.ui.ListFragmentDirections
import com.mbojec.halo.ui.SearchFragmentDirections

class ForecastListViewHolder(parent: ViewGroup, application: HaloApplication): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.search_city_list_item, parent, false)){

    private val cityAddress = itemView.findViewById<TextView>(R.id.cityAddress)
    private lateinit var forecastEntity: ForecastEntity

    init {
        itemView.setOnClickListener {
            val cityId = forecastEntity.cityId
            val action = ListFragmentDirections.actionListDestToMainDest(cityId)
            val handler = Handler()
            val task = Runnable {
                it.findNavController().navigate(action)
            }
            handler.postDelayed(task, 1000)
        }
    }


    fun bindTo(forecastEntity: ForecastEntity?){
        forecastEntity?.let {
            this.forecastEntity = it
            cityAddress.text = forecastEntity.feature.placeName
        }

    }
}