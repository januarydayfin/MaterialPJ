package com.krayapp.materialpj

import BottomNavigationDrawerFragment
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.krayapp.materialpj.ui.main.MainPictureFragment
import com.krayapp.materialpj.ui.main.SettingsFragment

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY_THEME = "Theme"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var currentTheme = PreferenceManager.getDefaultSharedPreferences(this)
            .getInt(KEY_THEME, R.style.AppThemeDay)
        setTheme(currentTheme)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainPictureFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_search -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            android.R.id.home -> {
                this.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}