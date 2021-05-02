package com.krayapp.materialpj.ui.main.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.krayapp.materialpj.BuildConfig
import com.krayapp.materialpj.R
import com.krayapp.materialpj.viewmodel.PictureInfo
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.current_image_viewpager.*
import kotlinx.android.synthetic.main.main_fragment.*

open class ViewPagerPictureFragment(private val pictureInfo: PictureInfo) : Fragment(){
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_image_viewpager, container, false)
    }

    fun getWhattaDay(): String? {
        return pictureInfo.whattaday
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setImage()
        setExplanation()
        setBottomSheetBehavior(bottom_sheet_container)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setImage() {
        PODImage.load(pictureInfo.url) {
            lifecycle(viewLifecycleOwner)
            error(R.drawable.error)
        }
    }

    private fun setExplanation() {
        bottom_sheet_description_header.text = pictureInfo.title
        bottom_sheet_description.text = pictureInfo.explanation
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}
