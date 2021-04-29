package com.krayapp.materialpj.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.chip.Chip
import com.krayapp.materialpj.MainActivity
import com.krayapp.materialpj.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.settings_layout.*

class SettingsFragment : Fragment() {
    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.settings_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        chipClickListener()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun chipClickListener() {
        theme_chip_group.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                when (it.tag.toString()) {
                    "day" -> {
                        PreferenceManager.getDefaultSharedPreferences(context).edit()
                            .putInt(MainActivity.KEY_THEME, R.style.AppTheme_Day).apply()
                    }
                    "night" -> {
                        PreferenceManager.getDefaultSharedPreferences(context).edit()
                            .putInt(MainActivity.KEY_THEME, R.style.AppTheme_Night).apply()
                    }
                    "moon" -> {
                        PreferenceManager.getDefaultSharedPreferences(context).edit()
                            .putInt(MainActivity.KEY_THEME, R.style.AppTheme_Moon).apply()
                    }
                    "mars" -> {
                        PreferenceManager.getDefaultSharedPreferences(context).edit()
                            .putInt(MainActivity.KEY_THEME, R.style.AppTheme_Mars).apply()
                    }
                }
            }
        }
        applyListener()
    }

    private fun applyListener() {
        applybtn.setOnClickListener {
            activity?.recreate()
        }
    }
}