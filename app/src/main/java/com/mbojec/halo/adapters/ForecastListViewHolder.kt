package com.mbojec.halo.adapters

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import com.mbojec.halo.database.entity.ForecastEntity
import com.mbojec.halo.ui.ListFragmentDirections

class ForecastListViewHolder(parent: ViewGroup, val application: HaloApplication): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.search_city_list_item, parent, false)), ItemTouchHelperViewHolder{
    override fun onItemSelected() {
        itemView.setBackgroundColor(ResourcesCompat.getColor(application.resources, R.color.bright_foreground_dark_disabled, null))
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(0)
    }

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
            handler.postDelayed(task, 500)
        }
    }


    fun bindTo(forecastEntity: ForecastEntity?){
        forecastEntity?.let {
            this.forecastEntity = it
            cityAddress.text = forecastEntity.feature.placeName
        }

    }
}