package com.mbojec.halo.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.recyclerview.widget.RecyclerView



class RecyclerViewConfiguration: BaseObservable() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<*>? = null

    @Bindable
    fun getLayoutManager(): RecyclerView.LayoutManager? {
        return layoutManager
    }

    @Bindable
    fun getAdapter(): RecyclerView.Adapter<*>? {
        return adapter
    }

    fun setConfig(layoutManager: RecyclerView.LayoutManager, adapter: RecyclerView.Adapter<*>) {
        this.layoutManager = layoutManager
        this.adapter = adapter
        notifyChange()
    }
}