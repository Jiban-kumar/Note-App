package com.example.note

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.note.entity.Note

class NoteAdapter(private val list: List<Note>) :RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val notetitle = itemView.findViewById<TextView>(R.id.noteTitle)
        val notesubtitle = itemView.findViewById<TextView>(R.id.noteSubTitle)
        val datetime = itemView.findViewById<TextView>(R.id.datetime)
        val layout = itemView.findViewById<RelativeLayout>(R.id.noteitem_layout)

        fun setData(note: Note){
            notetitle.text = note.title
            notesubtitle.text = note.subtitle
            datetime.text = note.datetime
            if(note.color != null){
                layout.setBackgroundColor(Color.parseColor(note.color))
            }
            else{
                layout.setBackgroundColor(Color.parseColor("#333333"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note_layout,null)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}