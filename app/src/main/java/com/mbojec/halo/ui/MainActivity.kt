package com.mbojec.halo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.mbojec.halo.*
import com.mbojec.halo.utils.PermissionUtils
import com.mbojec.halo.viewmodel.ActivityViewModel
import com.mbojec.halo.viewmodel.MainViewModel
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject lateinit var dispatchingAndroidFragmentInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>
    @Inject lateinit var application: HaloApplication
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_mainActivity)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ActivityViewModel::class.java)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(toolbar_mainActivity, navController)
        MainActivityStateListener.getInstanceAndInit(application, this, this)
        submitViewModel()
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)
        if (navController.currentDestination!!.id == R.id.main_dest){
            ActivityCompat.finishAfterTransition(this)
        } else {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        PermissionUtils.requestPermissionResultSolution(grantResults, this, this, application)
    }

    private fun submitViewModel(){
        viewModel.location.observe(this, Observer { location ->
            /**
             * Przy pierwszym uruchomieniu
             * @param location
             * jest null dlatego jest null-check
             * */
            location?.let { Toast.makeText(this, it.latitude.toString(), Toast.LENGTH_SHORT).show()}
        })

        viewModel.forecast.observe(this, Observer { forecast ->
            /**
             * Przy pierwszym uruchomieniu
             * @param forecast
             * jest null dlatego jest null-check
             * */
            forecast?.let { Toast.makeText(this, it.forecast.timezone, Toast.LENGTH_SHORT).show()}
        })
    }

    override fun supportFragmentInjector() = dispatchingAndroidFragmentInjector
}

