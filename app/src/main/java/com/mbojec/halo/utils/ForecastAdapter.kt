package com.mbojec.halo.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mbojec.halo.model.ForecastListItem
import com.mbojec.halo.ui.ForecastFragment


class ForecastAdapter(fragmentManager: FragmentManager, private val cityListForecast: List<ForecastListItem>): FragmentStatePagerAdapter(fragmentManager){

    override fun getItem(position: Int): Fragment {
        return if (position == 0 ){
            val fragment = ForecastFragment()
            val arguments = Bundle()
            arguments.putInt("CITY_ID", 1)
            fragment.arguments = arguments
            fragment
        } else {
            val fragment = ForecastFragment()
            val arguments = Bundle()
            arguments.putInt("CITY_ID", cityListForecast[position].cityId)
            fragment.arguments = arguments
            fragment
        }
    }

    override fun getCount(): Int {
        return cityListForecast.size
    }

}