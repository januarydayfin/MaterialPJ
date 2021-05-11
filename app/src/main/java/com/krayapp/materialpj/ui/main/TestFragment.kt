package com.krayapp.materialpj.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.krayapp.materialpj.R
import com.krayapp.materialpj.collapsing.Collapsing
import com.krayapp.materialpj.ui.main.notes.NoteActivity
import kotlinx.android.synthetic.main.test_fragment_layout.*

class TestFragment:Fragment() {
    companion object{
        fun newInstance() = TestFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chipClickListener()
    }

    private fun chipClickListener(){
        collapsing.setOnClickListener{
            startActivity(Intent(context, Collapsing::class.java))
        }
        notesActivity.setOnClickListener{
            startActivity(Intent(context, NoteActivity::class.java))
        }
    }
}