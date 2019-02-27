package com.mbojec.halo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import com.mbojec.halo.model.Forecast

class LongTermForecastListViewHolder(parent: ViewGroup, val application: HaloApplication): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.long_term_forecast_list_item, parent, false)){

    private val temp = itemView.findViewById<TextView>(R.id.temp)


    fun bindTo(dataDaily: Forecast.DataDaily){
            temp.text = dataDaily.temperatureHigh.toString()
    }
}