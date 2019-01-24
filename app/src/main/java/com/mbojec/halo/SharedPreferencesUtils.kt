package com.mbojec.halo

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesUtils @Inject constructor(private val haloApplication: HaloApplication, private val sharedPreferences: SharedPreferences, private val editor: SharedPreferences.Editor)
