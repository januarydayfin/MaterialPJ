package com.krayapp.materialpj

import BottomNavigationDrawerFragment
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceManager
import com.krayapp.materialpj.ui.main.MainPictureFragment
import com.krayapp.materialpj.ui.main.SettingsFragment
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY_THEME = "Theme"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var currentTheme = PreferenceManager.getDefaultSharedPreferences(this)
            .getInt(KEY_THEME, R.style.AppTheme_Day)
        setTheme(currentTheme)
        setContentView(R.layout.main_activity)
//        setNavigationButton()
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
            R.id.app_bar_home -> Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_youtube -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.motion_layer, SettingsFragment.newInstance())
                        .setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE))
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