package com.mbojec.halo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import com.mbojec.halo.model.Forecast

class ShortTermForecastAdapter(list: List<Forecast.DataHourly>, val application: HaloApplication): RecyclerView.Adapter<ShortTermForecastAdapter.ShortTermForecastListViewHolder>(){


    private var list: List<Forecast.DataHourly>? = null
    init {
        this.list = list
    }

    override fun getItemCount(): Int = if (list != null){
        list!!.size
    } else {
        0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortTermForecastListViewHolder =
        ShortTermForecastListViewHolder(parent)


    override fun onBindViewHolder(holder: ShortTermForecastListViewHolder, position: Int) {
        list?.get(position)?.let { holder.bindTo(it) }
    }

    class ShortTermForecastListViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.short_term_forecast_list_item, parent, false)){

        private val temp = itemView.findViewById<TextView>(R.id.shortTermCard)


        fun bindTo(dataHourly: Forecast.DataHourly){
            temp.text = dataHourly.temperature.toString()
        }
    }

}