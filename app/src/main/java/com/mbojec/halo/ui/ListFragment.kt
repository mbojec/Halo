package com.mbojec.halo.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mbojec.halo.viewmodel.ListViewModel
import com.mbojec.halo.R
import kotlinx.android.synthetic.main.list_fragment.*


class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun onResume() {
        super.onResume()
        floatingActionButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.search_dest)
        }
    }
}
