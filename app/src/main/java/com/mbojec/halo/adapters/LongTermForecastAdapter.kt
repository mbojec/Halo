package com.mbojec.halo.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.model.Forecast

class LongTermForecastAdapter(list: List<Forecast.DataDaily>, val application: HaloApplication): RecyclerView.Adapter<LongTermForecastListViewHolder>(){


    private var list: List<Forecast.DataDaily>? = null
    init {
        this.list = list
    }

    override fun getItemCount(): Int = if (list != null){
        list!!.size
    } else {
        0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LongTermForecastListViewHolder =
    LongTermForecastListViewHolder(parent, application)


    override fun onBindViewHolder(holder: LongTermForecastListViewHolder, position: Int) {
        list?.get(position)?.let { holder.bindTo(it) }
    }

}