package com.krayapp.materialpj.ui.main.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.krayapp.materialpj.viewmodel.PictureInfo

class ViewPagerAdapter(
    private val fragmentManager: FragmentManager,
    private val fragList: MutableList<ViewPagerPictureFragment>
) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return fragList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragList[position].getWhattaDay()
    }

    override fun getItem(position: Int): Fragment {
        return fragList[position]
    }

}