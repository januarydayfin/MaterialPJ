package com.krayapp.materialpj.ui.main.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.krayapp.materialpj.R
import kotlinx.android.synthetic.main.note_template.view.*

class NoteAdapter(
    private var data: MutableList<NotesData>,
    private var clickListener: MyClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NotesViewHolder(
            inflater.inflate(R.layout.note_template, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as NotesViewHolder
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(notesData: NotesData) {
            itemView.descriptionList.setText(notesData.description)
            var favFlag = notesData.favouriteFlag
            if (favFlag) {
                itemView.favFlag.setImageResource(R.drawable.ic_staron)
            } else {
                itemView.favFlag.setImageResource(R.drawable.ic_staroff)
            }

            itemView.favFlag.setOnClickListener{
                favFlag =!favFlag
                if (favFlag) {
                    itemView.favFlag.setImageResource(R.drawable.ic_staron)
                } else {
                    itemView.favFlag.setImageResource(R.drawable.ic_staroff)
                }
            }
            itemView.cardTempl.setOnClickListener {
                clickListener.onClick(notesData)
            }
        }
    }

    interface MyClickListener {
        fun onClick(notesData: NotesData)
    }
}