package com.krayapp.materialpj.ui.main.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.krayapp.materialpj.BuildConfig
import com.krayapp.materialpj.R
import com.krayapp.materialpj.SquareImage
import com.krayapp.materialpj.Toast
import com.krayapp.materialpj.ui.main.MainPictureFragment
import com.krayapp.materialpj.viewmodel.PictureInfo
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.current_image_viewpager.*
import kotlinx.android.synthetic.main.main_fragment.*

class ViewPagerPictureFragment : Fragment() {
    private var pictureInfo: PictureInfo? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        private const val PAGER_KEY = "PAGER_KEY"
        fun newInstance(pictureInfo: PictureInfo): ViewPagerPictureFragment {
            val newFrag = ViewPagerPictureFragment()
            val bundle = Bundle()
            bundle.putParcelable(PAGER_KEY, pictureInfo)
            newFrag.arguments = bundle
            return newFrag
        }

        var isExpanded = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.pictureInfo = arguments?.getParcelable(PAGER_KEY)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_image_viewpager, container, false)
    }

    fun getWhattaDay(): String? {
        return pictureInfo?.whattaday
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage()
        setExplanation()
        setBottomSheetBehavior(bottom_sheet_container)
        imageClickListener()
    }

    private fun setImage() {
        PODImage.load(pictureInfo?.url) {
            lifecycle(viewLifecycleOwner)
            error(R.drawable.error)
        }
    }

    private fun setExplanation() {
        bottom_sheet_description_header.text = pictureInfo?.title
        bottom_sheet_description.text = pictureInfo?.explanation
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
    private fun imageClickListener() {
        PODImage.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                current_image_layout, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams = PODImage.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            PODImage.layoutParams = params
            PODImage.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
    }
}

