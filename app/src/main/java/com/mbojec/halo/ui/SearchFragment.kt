package com.mbojec.halo.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mbojec.halo.R
import com.mbojec.halo.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*


class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        floatingActionButton2.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.main_dest)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchView = ((menu.findItem(R.id.app_bar_search)).actionView as SearchView)
        searchView.isIconified = false
    }

}
