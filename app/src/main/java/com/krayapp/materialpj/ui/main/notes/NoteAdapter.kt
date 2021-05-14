package com.krayapp.materialpj.ui.main.notes

import android.annotation.SuppressLint
import android.view.KeyEvent.ACTION_DOWN
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.krayapp.materialpj.R
import kotlinx.android.synthetic.main.note_template.view.*

class NoteAdapter(
    private var data: MutableList<Pair<NotesData, Boolean>>,
    private var clickListener: MyClickListener,
    private val dragListener: OnStartDragListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), NoteFragment.ItemTouchHelperAdapter {
    companion object{
        private var favSort:Boolean = false
    }
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NotesViewHolder(
            inflater.inflate(R.layout.note_template, parent, false) as View
        )
    }

    private fun setData(newData: MutableList<Pair<NotesData, Boolean>>) {
        this.data = newData
    }

    fun sortByFav() {
        favSort = !favSort
        if (favSort){
            data.sortBy { !it.first.favouriteFlag }
        }else{
            data.sortBy { it.first.title }
        }
        notifyDataSetChanged()
    }

    fun appendItem() {
        data.add(
            itemCount,
            Pair(
                NotesData(
                    "Hello",
                    "Simple hello some text, for test our description edit text",
                    true
                ), false
            )
        )
        notifyItemInserted(itemCount)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as NotesViewHolder
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view),
        NoteFragment.ItemTouchHelperViewHolder {
        @SuppressLint("ClickableViewAccessibility")
        fun onBind(notesData: Pair<NotesData, Boolean>) {
            itemView.bottom_drag_sandwich.setOnTouchListener { _, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }
            itemView.titleNote.setText(notesData.first.title)
            itemView.descriptionNote.setText(notesData.first.description)
            var favFlag = notesData.first.favouriteFlag
            if (favFlag) {
                itemView.favFlag.setImageResource(R.drawable.ic_staron)
            } else {
                itemView.favFlag.setImageResource(R.drawable.ic_staroff)
            }
            itemView.descriptionNote.visibility = if (notesData.second) View.VISIBLE else View.GONE
            itemView.titleNote.setOnClickListener {
                toggleText()
            }

            itemView.favFlag.setOnClickListener {
                favFlag = !favFlag
                if (favFlag) {
                    itemView.favFlag.setImageResource(R.drawable.ic_staron)
                } else {
                    itemView.favFlag.setImageResource(R.drawable.ic_staroff)
                }
                notesData.first.favouriteFlag = favFlag
            }

            itemView.deleteNoteChip.setOnClickListener {
                removeItem()
            }
            itemView.cardTempl.setOnClickListener {
                clickListener.onClick(notesData.first)
            }
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() {

        }

        override fun onItemClear() {
            TODO("Not yet implemented")
        }

    }


    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    interface MyClickListener {
        fun onClick(notesData: NotesData)
    }

}