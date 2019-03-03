package com.mbojec.halo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import com.mbojec.halo.model.Forecast
import com.mbojec.halo.model.ShortTermForecast

class ShortTermForecastAdapter(list: List<ShortTermForecast>, val application: HaloApplication): RecyclerView.Adapter<ShortTermForecastAdapter.ShortTermForecastListViewHolder>(){


    private var list: List<ShortTermForecast>? = null
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

        private val temp = itemView.findViewById<TextView>(R.id.tv_detail_temp)
        private val hour = itemView.findViewById<TextView>(R.id.tv_detail_hour)
        private val image = itemView.findViewById<ImageView>(R.id.iv_detail_forecast_image)


        fun bindTo(shortTermForecast: ShortTermForecast){
            temp.text = shortTermForecast.temp
            hour.text = shortTermForecast.hour
            Glide.with(image.context).load(shortTermForecast.weatherImage).into(image)
        }
    }

}