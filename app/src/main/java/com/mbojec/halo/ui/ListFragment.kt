package com.mbojec.halo.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mbojec.halo.adapters.ForecastListAdapter
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.viewmodel.ListViewModel
import com.mbojec.halo.R
import com.mbojec.halo.dagger.Injectable
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject


class ListFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: ListViewModel
    @Inject lateinit var application: HaloApplication

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        floatingActionButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.search_dest)
        }
    }

    override fun onStart() {
        super.onStart()
        ForecastListAdapter.getInstanceAndInit(this, viewModel, activity!!, application)
    }


    override fun onStop() {
        super.onStop()
        ForecastListAdapter.clearInstance()
    }
}
