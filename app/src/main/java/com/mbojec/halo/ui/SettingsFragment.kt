package com.mbojec.halo.ui


import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.mbojec.halo.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}