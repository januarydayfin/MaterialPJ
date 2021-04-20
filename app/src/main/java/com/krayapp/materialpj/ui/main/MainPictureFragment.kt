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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.krayapp.materialpj.R
import com.krayapp.materialpj.viewmodel.MainViewModel
import com.krayapp.materialpj.viewmodel.PictureData
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainPictureFragment : Fragment() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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
        viewModel.getLiveData(null).observe(viewLifecycleOwner, Observer { renderData(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        setBottomSheetBehavior(bottom_sheet_container)
    }

    private fun renderData(data: PictureData) {
        when (data) {
            is PictureData.Success -> {
                PODImage.visibility = View.VISIBLE
                included_loading.visibility = View.GONE
                val serverResponse = data.serverResponseData
                val url = serverResponse.url
                val title = serverResponse.title
                val explanation = serverResponse.explanation
                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, "Нет данных", Toast.LENGTH_SHORT).show()
                } else {
                    bottom_sheet_description_header.text = title
                    bottom_sheet_description.text = explanation
                    PODImage.load(url) {
                        lifecycle(viewLifecycleOwner)
                        error(R.drawable.error)
                    }
                }
            }
            is PictureData.Error -> {
                Toast.makeText(context, data.error.toString(), Toast.LENGTH_SHORT).show()
            }
            is PictureData.Loading -> {
                PODImage.visibility = View.GONE
                included_loading.visibility = View.VISIBLE
            }

        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

}