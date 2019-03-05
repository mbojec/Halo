package com.mbojec.halo.ui


import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.mbojec.halo.*
import com.mbojec.halo.utils.FirebaseRemoteConfigUtils
import com.mbojec.halo.utils.NotificationUtils
import com.mbojec.halo.utils.PermissionUtils
import com.mbojec.halo.viewmodel.ActivityViewModel
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
        supportActionBar!!.title = ""
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(toolbar_mainActivity, navController)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationUtils.setNotification(application)
            NotificationUtils.logUserTokenId()
        }
        MainActivityStateListener.getInstanceAndInit(application, this, this, FirebaseRemoteConfigUtils.setRemoteConfig())
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT
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

    override fun supportFragmentInjector() = dispatchingAndroidFragmentInjector
}

