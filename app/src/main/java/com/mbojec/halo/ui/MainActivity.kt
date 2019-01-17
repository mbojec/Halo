package com.mbojec.halo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.mbojec.halo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_mainActivity)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(toolbar_mainActivity, navController)
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)
        if (navController.currentDestination!!.id == R.id.main_dest){
            ActivityCompat.finishAfterTransition(this)
        } else {
            super.onBackPressed()
        }
    }
}

