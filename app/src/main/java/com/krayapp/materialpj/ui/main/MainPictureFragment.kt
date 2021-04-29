package com.krayapp.materialpj.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.krayapp.materialpj.MainActivity
import com.krayapp.materialpj.R
import com.krayapp.materialpj.ui.main.viewpager.ViewPagerAdapter
import com.krayapp.materialpj.ui.main.viewpager.ViewPagerPictureFragment
import com.krayapp.materialpj.viewmodel.MainViewModel
import com.krayapp.materialpj.viewmodel.PictureData
import com.krayapp.materialpj.viewmodel.PictureInfo
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainPictureFragment : Fragment() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var fragListForAdapter: MutableList<ViewPagerPictureFragment> = mutableListOf()

    companion object {
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
//        viewModel.getLiveData(null).observe(viewLifecycleOwner, Observer { renderData(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        view_pager.adapter = ViewPagerAdapter(childFragmentManager, fragListForAdapter)
        setCircleIndicator()
        setNavigationButton()
        setBottomSheetBehavior(bottom_sheet_container)
        setBottomAppBar(view)
    }


    private fun renderData(data: PictureData) {
        when (data) {
            is PictureData.Success -> {
//                PODImage.visibility = View.VISIBLE
                included_loading.visibility = View.GONE
                val serverResponse = data.serverResponseData
                val url = serverResponse.url
                val title = serverResponse.title
                val explanation = serverResponse.explanation
                val date = serverResponse.date
                val mediaType = serverResponse.mediaType
                val hdurl = serverResponse.hdurl
                val pictureInfo = PictureInfo(date, explanation, mediaType, title, url, hdurl)
                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, "Нет данных", Toast.LENGTH_SHORT).show()
                } else {
                    bottom_sheet_description_header.text = title
                    bottom_sheet_description.text = explanation
                    fragListForAdapter.add(ViewPagerPictureFragment(pictureInfo))
                    /*PODImage.load(url) {.s
                        lifecycle(viewLifecycleOwner)
                        error(R.drawable.error)
                    }*/
                }
            }
            is PictureData.Error -> {
                Toast.makeText(context, data.error.toString(), Toast.LENGTH_SHORT).show()
            }
            is PictureData.Loading -> {
//                PODImage.visibility = View.GONE
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
        while (i >= -2) {
            if (i == 0) {
                date = null
            } else {
                cal.add(Calendar.DATE, i)
                date = dateFormat.format(cal.time)
            }
            i--
            viewModel.getLiveData(date).observe(
                viewLifecycleOwner,
                Observer { renderData(it) })
        }

    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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
                            .replace(R.id.main_frag_container, MainPictureFragment.newInstance())
                            .setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE))
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                    true
                }
                R.id.app_bar_search -> {
                    Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.app_bar_settings -> {
                    childFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.main_frag_container, SettingsFragment.newInstance())
                            .setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE))
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                    true
                }
                else -> false
            }
        }
    }
    private fun setCircleIndicator(){
        indicator.setViewPager(view_pager)
        view_pager.adapter?.registerDataSetObserver(indicator.dataSetObserver)
    }
}