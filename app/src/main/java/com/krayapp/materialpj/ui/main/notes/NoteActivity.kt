package com.krayapp.materialpj.ui.main.notes

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.krayapp.materialpj.R
import kotlinx.android.synthetic.main.notes_layout.*

class NoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.notes_layout)
        recycler.adapter = NoteAdapter(listOf(
            NotesData("Почесать кота", false),
            NotesData("Выгулять кота", false),
            NotesData("Поговорить с котом", false),
            NotesData("Обнять кота", false)
        ), object : NoteAdapter.MyClickListener{
            override fun onClick(notesData: NotesData) {
                Toast.makeText(parent.applicationContext, notesData.description, Toast.LENGTH_SHORT).show()
            }
        })
    }
}