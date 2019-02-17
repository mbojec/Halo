package com.mbojec.halo.adapters

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.model.SearchCityList
import com.mbojec.halo.model.SingletonAdapterHolder
import com.mbojec.halo.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*

class SearchCityListAdapter(lifecycleOwner: LifecycleOwner, viewModel: SearchViewModel, activity: FragmentActivity, val application: HaloApplication): RecyclerView.Adapter<SearchCityListViewHolder>(){
    private var list: SearchCityList? = null

    companion object : SingletonAdapterHolder<SearchCityListAdapter, LifecycleOwner, SearchViewModel, FragmentActivity, HaloApplication>(::SearchCityListAdapter)

    init {
        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        activity.searchCityListRecycleView.layoutManager = layoutManager
        activity.searchCityListRecycleView.adapter = this

        viewModel.searchCityList.observe(lifecycleOwner, Observer {it ->
            it?.let { loadList(it) }?:clearList()
        })
    }

    private fun loadList(searchCityList: SearchCityList){
        list = searchCityList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.features?.size?: 0
    }

    override fun onBindViewHolder(holder: SearchCityListViewHolder, position: Int) {
        list?.features?.get(position)?.let { holder.bindTo(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCityListViewHolder =
        SearchCityListViewHolder(parent, application)

    private fun clearList(){
        list = null
        notifyDataSetChanged()
    }

}