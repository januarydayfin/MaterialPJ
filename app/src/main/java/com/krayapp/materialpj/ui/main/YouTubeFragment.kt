package com.krayapp.materialpj.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.krayapp.materialpj.R
import kotlinx.android.synthetic.main.youtube_layout.*

class YouTubeFragment : Fragment() {
    companion object{
        fun newInstance() = YouTubeFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.youtube_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        webview_player_view.settings.javaScriptEnabled = true
        webview_player_view.loadUrl("https://www.youtube.com/embed/dQw4w9WgXcQ")
        super.onViewCreated(view, savedInstanceState)
    }
}