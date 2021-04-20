package com.krayapp.materialpj.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.krayapp.materialpj.R
import com.krayapp.materialpj.viewmodel.MainViewModel
import com.krayapp.materialpj.viewmodel.PictureData
import kotlinx.android.synthetic.main.main_fragment.*

class MainPictureFragment : Fragment() {

    companion object {
        fun newInstance() = MainPictureFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getLiveData(null).observe(viewLifecycleOwner, Observer { renderData(it) })
    }

    private fun renderData(data: PictureData){
        when(data){
            is PictureData.Success ->{
                PODImage.visibility = View.VISIBLE
                included_loading.visibility = View.GONE
                val serverResponse = data.serverResponseData
                val url = serverResponse.url
                if(url.isNullOrEmpty()){
                    Toast.makeText(context,"Нет данных", Toast.LENGTH_SHORT).show()
                }else{
                    PODImage.load(url){
                        lifecycle(viewLifecycleOwner)
                        error(R.drawable.error)
                    }
                }
            }
            is PictureData.Error ->{
                Toast.makeText(context, data.error.toString(), Toast.LENGTH_SHORT).show()
            }
            is PictureData.Loading ->{
                PODImage.visibility = View.GONE
                included_loading.visibility = View.VISIBLE
            }

        }
    }

}