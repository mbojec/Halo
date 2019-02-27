package com.mbojec.halo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import com.mbojec.halo.model.Forecast

class LongTermForecastAdapter(list: List<Forecast.DataDaily>, val application: HaloApplication): RecyclerView.Adapter<LongTermForecastAdapter.LongTermForecastListViewHolder>(){


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
    LongTermForecastListViewHolder(parent)


    override fun onBindViewHolder(holder: LongTermForecastListViewHolder, position: Int) {
        list?.get(position)?.let { holder.bindTo(it) }
    }

    class LongTermForecastListViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.long_term_forecast_list_item, parent, false)){

        private val temp = itemView.findViewById<TextView>(R.id.temp)


        fun bindTo(dataDaily: Forecast.DataDaily){
            temp.text = dataDaily.temperatureHigh.toString()
        }
    }

}