package com.mbojec.halo.adapters

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import com.mbojec.halo.database.entity.ForecastEntity
import com.mbojec.halo.ui.ListFragmentDirections
import com.mbojec.halo.utils.WeatherDataConverter
import java.util.*

class ForecastListAdapter(val application: HaloApplication): RecyclerView.Adapter<ForecastListAdapter.ForecastListViewHolder>(), ItemTouchHelperAdapter{

    private var list: ArrayList<ForecastEntity>? = null

    fun loadList(forecastList: ArrayList<ForecastEntity>){
        list = forecastList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListViewHolder =
        ForecastListViewHolder(parent, application, this)

    override fun getItemCount(): Int {
        return list?.size?: 0
    }

    override fun onBindViewHolder(holder: ForecastListViewHolder, position: Int) {
        list?.get(position)?.let { holder.bindTo(it) }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(list, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(list, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        list!!.removeAt(position)
        notifyItemRemoved(position)
        updateList()
    }

    private fun updateList(){
        for (i in 0 until list!!.size ){
            list!![i].rowId = i + 1
        }
        application.dataRepository.updateList(list!!)
    }


    class ForecastListViewHolder(parent: ViewGroup, val application: HaloApplication, val adapter: ForecastListAdapter): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.forecast_city_list_item, parent, false)), ItemTouchHelperViewHolder{

        private var cityCard: CardView = itemView.findViewById<CardView>(R.id.city_card_view)
        private val cityAddress = itemView.findViewById<TextView>(R.id.tv_list_item_city_name)
        private val cityTemp = itemView.findViewById<TextView>(R.id.tv_list_item_temp)
        private val weatherImage = itemView.findViewById<ImageView>(R.id.iv_list_item_forecast)
        private lateinit var forecastEntity: ForecastEntity

        init {
            cityCard.setOnClickListener {
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
                cityCard.setCardBackgroundColor(WeatherDataConverter.getProperBackgroundColor(forecastEntity, application))
                this.forecastEntity = it
                cityAddress.text = forecastEntity.feature.textPl
                cityTemp.text = com.mbojec.halo.utils.DataUtils.formatTemperature(application, forecastEntity.forecast.currently?.temperature)
                Glide.with(itemView).load(com.mbojec.halo.utils.DataUtils.getImageResourceForWeatherCondition(forecastEntity.forecast.currently?.icon)).into(weatherImage)

            }

        }

        override fun onItemSelected() {
        }

        override fun onItemClear() {
            adapter.updateList()
        }
    }

}