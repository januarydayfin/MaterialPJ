package com.krayapp.materialpj.ui.main.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import com.krayapp.materialpj.R
import com.krayapp.materialpj.viewmodel.PictureInfo
import kotlinx.android.synthetic.main.current_image_viewpager.*

open class ViewPagerPictureFragment(private val pictureInfo: PictureInfo) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_image_viewpager, container, false)
    }

    fun getTitle():String?{
        return pictureInfo.title
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        PODImage.load(pictureInfo.url) {
            lifecycle(viewLifecycleOwner)
            error(R.drawable.error)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
