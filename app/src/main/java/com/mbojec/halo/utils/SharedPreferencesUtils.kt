package com.mbojec.halo.utils

import android.content.SharedPreferences
import com.mbojec.halo.HaloApplication
import javax.inject.Inject

class SharedPreferencesUtils @Inject constructor(private val haloApplication: HaloApplication, private val sharedPreferences: SharedPreferences, private val editor: SharedPreferences.Editor)
