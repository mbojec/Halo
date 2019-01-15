package com.mbojec.halo

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class AboutFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        setPreferencesFromResource(R.xml.about, rootKey)
    }
}