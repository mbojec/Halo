package com.mbojec.halo.ui

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mbojec.halo.BuildConfig
import com.mbojec.halo.R

class AboutFragmentPref : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.about, rootKey)
        val versionPreference = findPreference<Preference>(getString(R.string.version_key))
        setPreferenceSummary(versionPreference, BuildConfig.VERSION_NAME)
    }

    private fun setPreferenceSummary(preference: Preference, value: Any) {
        val stringValue = value.toString()

        if (preference is ListPreference) {
            val prefIndex = preference.findIndexOfValue(stringValue)
            if (prefIndex >= 0) {
                preference.setSummary(preference.entries[prefIndex])
            }
        } else {
            preference.summary = stringValue
        }
    }
}