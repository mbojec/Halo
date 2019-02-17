package com.mbojec.halo.adapters

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

    private var list: List<ForecastEntity>? = null
    private var touchHelper: ItemTouchHelper

    companion object : SingletonAdapterHolder<ForecastListAdapter, LifecycleOwner, ListViewModel, FragmentActivity, HaloApplication>(::ForecastListAdapter)

    init {
        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        activity.forecast_list.layoutManager = layoutManager
        activity.forecast_list.adapter = this
        touchHelper = ItemTouchHelper(SimpleItemTouchHelperCallback(this))
        touchHelper.attachToRecyclerView(activity.forecast_list)

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

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition + 1 until toPosition) {
//                Collections.swap(list, i, i + 1)
                list!![i].rowId -= 1
            }
//            list!![fromPosition].rowId = toPosition
        } else {
            for (i in fromPosition - 1 downTo toPosition) {
//                Collections.swap(list, i, i-1)
                list!![i].rowId += 1
            }
//            list!![fromPosition].rowId = toPosition
        }
        list!![fromPosition].rowId = toPosition
        notifyItemMoved(fromPosition, toPosition)
//        application.database.forecastDao().updateData(list!!)
        application.dataRepository.updateList(list!!)
        notifyDataSetChanged()
        return true
    }

    override fun onItemDismiss(position: Int) {

    }

}