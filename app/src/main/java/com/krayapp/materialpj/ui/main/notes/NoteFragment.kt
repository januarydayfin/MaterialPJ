package com.krayapp.materialpj.ui.main.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.krayapp.materialpj.R
import kotlinx.android.synthetic.main.notes_layout.*

class NoteFragment: Fragment() {
    companion object{
        fun newInstance() = NoteFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter = NoteAdapter(mutableListOf(
            NotesData("Почесать кота", false),
            NotesData("Выгулять кота", false),
            NotesData("Поговорить с котом", false),
            NotesData("Обнять кота", false)
        ), object : NoteAdapter.MyClickListener{
            override fun onClick(notesData: NotesData) {
                Toast.makeText(context, notesData.description, Toast.LENGTH_SHORT).show()
            }
        })
    }
}