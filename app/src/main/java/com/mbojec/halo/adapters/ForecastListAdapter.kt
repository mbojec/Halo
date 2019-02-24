package com.mbojec.halo.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.database.entity.ForecastEntity
import com.mbojec.halo.model.SingletonAdapterHolder
import com.mbojec.halo.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import java.util.*

class ForecastListAdapter(lifecycleOwner: LifecycleOwner, viewModel: ListViewModel, activity: FragmentActivity, val application: HaloApplication): RecyclerView.Adapter<ForecastListViewHolder>(), ItemTouchHelperAdapter{

    private var list: ArrayList<ForecastEntity>? = null
    private var touchHelper: ItemTouchHelper

    companion object : SingletonAdapterHolder<ForecastListAdapter, LifecycleOwner, ListViewModel, FragmentActivity, HaloApplication>(::ForecastListAdapter)

    init {
        val layoutManager = LinearLayoutManager(activity as Context, RecyclerView.VERTICAL, false)
        activity.forecast_list.layoutManager = layoutManager
        activity.forecast_list.adapter = this
        touchHelper = ItemTouchHelper(SimpleItemTouchHelperCallback(this))
        touchHelper.attachToRecyclerView(activity.forecast_list)

        viewModel.forecastList.observe(lifecycleOwner, Observer {
            if (list == null){
                it?.let { loadList(it as ArrayList<ForecastEntity>) }
            }
        })
    }

    private fun loadList(forecastList: ArrayList<ForecastEntity>){
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
        updateList()
        return true
    }

    override fun onItemDismiss(position: Int) {
        list!!.removeAt(position)
        notifyItemRemoved(position)
        updateList()
    }

    private fun updateList(){
        for (i in 0 until list!!.size ){
            list!![i].rowId = i
        }
        application.dataRepository.updateList(list!!)
    }

}