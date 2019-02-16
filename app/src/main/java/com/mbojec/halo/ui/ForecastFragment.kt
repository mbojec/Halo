package com.mbojec.halo.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mbojec.halo.viewmodel.ForecastViewModel
import com.mbojec.halo.R
import com.mbojec.halo.dagger.Injectable
import kotlinx.android.synthetic.main.forecast_fragment.*
import javax.inject.Inject


class ForecastFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: ForecastViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ForecastViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val arguments: Bundle? = arguments
        val cityId :Int = arguments?.getInt("CITY_ID")?:1
        viewModel.setId(cityId)
        submitToViewModel()
        return inflater.inflate(R.layout.forecast_fragment, container, false)
    }

    private fun submitToViewModel(){
        viewModel.forecast.observe(this, Observer {
//            it?.let { Toast.makeText(activity, it.forecast.timezone, Toast.LENGTH_SHORT).show() }
            it?.let { text_view.text = it.feature.placeName }
        })
    }

}
