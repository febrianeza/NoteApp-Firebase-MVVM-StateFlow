package com.ezafebrian.firebasemvvmstateflow.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezafebrian.firebasemvvmstateflow.data.model.Note
import com.ezafebrian.firebasemvvmstateflow.databinding.NoteRecyclerItemBinding

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val notes = ArrayList<Note>()
    internal var onItemClick: ((Note) -> Unit)? = null
    internal var onItemLongClick: ((Note) -> Unit)? = null

    fun populateNotes(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: NoteRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                textNoteTitle.text = note.title
                textNoteDate.text = note.date
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(notes[adapterPosition])
            }

            binding.root.setOnLongClickListener {
                onItemLongClick?.invoke(notes[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            NoteRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size
}