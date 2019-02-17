package com.mbojec.halo.adapters

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.database.entity.ForecastEntity
import com.mbojec.halo.model.SingletonAdapterHolder
import com.mbojec.halo.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.list_fragment.*

class ForecastListAdapter(lifecycleOwner: LifecycleOwner, viewModel: ListViewModel, activity: FragmentActivity, val application: HaloApplication): RecyclerView.Adapter<ForecastListViewHolder>(){
    private var list: List<ForecastEntity>? = null

    companion object : SingletonAdapterHolder<ForecastListAdapter, LifecycleOwner, ListViewModel, FragmentActivity, HaloApplication>(::ForecastListAdapter)

    init {
        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        activity.forecast_list.layoutManager = layoutManager
        activity.forecast_list.adapter = this
        viewModel.forecastList.observe(lifecycleOwner, Observer {
            it?.let { loadList(it) }?:clearList()
        })
    }

    private fun loadList(forecastList: List<ForecastEntity>){
        list = forecastList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListViewHolder =
        ForecastListViewHolder(parent, application)

    override fun getItemCount(): Int {
        return list?.size?: 0
    }

    override fun onBindViewHolder(holder: ForecastListViewHolder, position: Int) {
        list?.get(position)?.let { holder.bindTo(it) }
    }

    private fun clearList(){
        list = null
        notifyDataSetChanged()
    }

}