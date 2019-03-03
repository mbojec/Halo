package com.mbojec.halo.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.mbojec.halo.R

class SettingsFragmentPref: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}