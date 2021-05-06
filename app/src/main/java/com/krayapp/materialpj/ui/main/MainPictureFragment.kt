package com.krayapp.materialpj.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.krayapp.materialpj.MainActivity
import com.krayapp.materialpj.R
import com.krayapp.materialpj.ui.main.viewpager.ViewPagerAdapter
import com.krayapp.materialpj.ui.main.viewpager.ViewPagerPictureFragment
import com.krayapp.materialpj.viewmodel.MainViewModel
import com.krayapp.materialpj.viewmodel.PictureData
import com.krayapp.materialpj.viewmodel.PictureInfo
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainPictureFragment : Fragment() {
    private var fragListForAdapter: MutableList<ViewPagerPictureFragment> = mutableListOf()

    companion object {
        var visible = false
        fun newInstance() = MainPictureFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        getFragListFromServer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        view_pager.adapter = ViewPagerAdapter(childFragmentManager, fragListForAdapter)
        setNavigationButton()
        setCircleIndicator()
        setBottomAppBar(view)
        wikiBtnClickListener()
        startChip.setOnClickListener{viewPageVisible()}
    }


    private fun renderData(data: PictureData) {
        when (data) {
            is PictureData.Success -> {
                included_loading.visibility = View.GONE
                val serverResponse = data.serverResponseData
                val url = serverResponse.url
                val title = serverResponse.title
                val explanation = serverResponse.explanation
                val date = serverResponse.date
                val mediaType = serverResponse.mediaType
                val hdurl = serverResponse.hdurl
                val whattaday = data.whattaDay
                val pictureInfo =
                    PictureInfo(date, explanation, mediaType, title, url, hdurl, whattaday)
                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, "Нет данных", Toast.LENGTH_SHORT).show()
                } else {
                    fragListForAdapter.add(ViewPagerPictureFragment(pictureInfo))
                }
            }
            is PictureData.Error -> {
                Toast.makeText(context, data.error.toString(), Toast.LENGTH_SHORT).show()
            }
            is PictureData.Loading -> {
                included_loading.visibility = View.VISIBLE
            }

        }
        view_pager.adapter?.notifyDataSetChanged()
    }

    private fun getFragListFromServer() {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val cal: Calendar = Calendar.getInstance()
        var date: String? = null
        var i = 0
        var whattaDay: String? = null
        while (i >= -2) {
            if (i == 0) {
                date = null
                whattaDay = "Today"
            } else {
                if (i == -1) {
                    whattaDay = "Yesterday"
                } else {
                    whattaDay = "Yeyestarday"
                }
                cal.add(Calendar.DATE, i)
                date = dateFormat.format(cal.time)
            }
            i--
            viewModel.getLiveData(date, whattaDay).observe(
                viewLifecycleOwner,
                Observer { renderData(it) })
        }

    }


    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
    }

    private fun setNavigationButton() {
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.app_bar_home -> {
                    childFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.motion_layer, MainPictureFragment.newInstance())
                            .setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE))
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                    true
                }
                R.id.app_bar_youtube -> {
//                    motion_layer.visibility = View.GONE
                    childFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.motion_layer, YouTubeFragment.newInstance())
                            .setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE))
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                    true
                }
                R.id.app_bar_settings -> {
//                    motion_layer.visibility = View.GONE
                    childFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.motion_layer, SettingsFragment.newInstance())
                            .setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE))
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                    true
                }
                R.id.test_frag_menu -> {
//                    viewPageVisible()
                     childFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.motion_layer, TestFragment.newInstance())
                            .setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE))
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                    true
                }
                else -> false
            }
        }
        bottom_navigation_view.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.test_frag_menu -> {
                    viewPageVisible()
                }
            }
        }
    }

    private fun setCircleIndicator() {
        indicator.setViewPager(view_pager)
        view_pager.adapter?.registerDataSetObserver(indicator.dataSetObserver)
    }

    private fun viewPageVisible() {
        visible = !visible
        TransitionManager.beginDelayedTransition(motion_layer, Slide(Gravity.BOTTOM))
        wiki_button.visibility = if (!visible) View.VISIBLE else View.GONE
        view_pager.visibility = if (visible) View.VISIBLE else View.GONE
        input_layout.visibility = if (visible) View.VISIBLE else View.GONE
        indicator.visibility = if (visible) View.VISIBLE else View.GONE
        startChip.visibility = View.GONE
    }
    private fun wikiBtnClickListener(){
        val wikiVisible = !visible
        wiki_button.setOnClickListener{
            TransitionManager.beginDelayedTransition(motion_layer, Slide(Gravity.START))
            startChip.visibility = View.GONE
            input_layout.visibility = if (wikiVisible) View.VISIBLE else View.GONE
            seeNext.visibility = if (wikiVisible) View.VISIBLE else View.GONE
            wiki_button.visibility = if (!wikiVisible) View.VISIBLE else View.GONE
            seeChipListener()
        }
    }

    private fun seeChipListener(){
        visible = !visible
        seeNext.setOnClickListener{
            TransitionManager.beginDelayedTransition(motion_layer, Slide(Gravity.END))
            seeNext.visibility = View.GONE
            wiki_button.visibility = if (!visible) View.VISIBLE else View.GONE
            view_pager.visibility = if (visible) View.VISIBLE else View.GONE
            indicator.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }
}

