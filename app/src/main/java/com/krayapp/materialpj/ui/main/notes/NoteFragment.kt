package com.krayapp.materialpj.ui.main.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.krayapp.materialpj.R
import kotlinx.android.synthetic.main.notes_layout.*

class NoteFragment : Fragment() {
    companion object {
        fun newInstance() = NoteFragment()
    }
    lateinit var itemTouchHelper: ItemTouchHelper
    private val adapter = NoteAdapter(mutableListOf(
        Pair(NotesData("Почесать кота", "Надо котю причесать, тутутуту", false), false),
        Pair(NotesData("Выгулять кота", "Пойду на прогулочку с котом, алялял", false), false),
        Pair(
            NotesData(
                "Поговорить с котом",
                "Привет, дорогой кот, как у тебя дела? Давно не виделись, уже минут 5...",
                false
            ), false
        ),
        Pair(NotesData("Обнять кота", "Иди обниму, маленький пушистик", false), false)
    ), object : NoteAdapter.MyClickListener {
        override fun onClick(notesData: NotesData) {
            Toast.makeText(context, notesData.description, Toast.LENGTH_SHORT).show()
        }
    }, object : NoteAdapter.OnStartDragListener{
        override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper.startDrag(viewHolder)
        }

    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter = adapter
        addButtonListener()
        itemTouchHelper = ItemTouchHelper(ItemTouchCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recycler)
    }

    private fun addButtonListener() {
        AddChip.setOnClickListener {
            adapter.appendItem()
        }
    }

    interface ItemTouchHelperAdapter {
        fun onItemMove(fromPosition: Int, toPosition: Int)

        fun onItemDismiss(position: Int)
    }

    interface ItemTouchHelperViewHolder {

        fun onItemSelected()

        fun onItemClear()
    }
}