package sinno.seacs.mvvmappdemo.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import sinno.seacs.mvvmappdemo.R
import sinno.seacs.mvvmappdemo.models.EntityNote
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class NotesRecyclerViewAdapter(
    private val context : Context,
    private val listener: NotesClickListener
) : RecyclerView.Adapter<NotesRecyclerViewAdapter.NotesViewHolder>() {

    private val notesList = ArrayList<EntityNote>()
    private val fullNotesList = ArrayList<EntityNote>()

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notesLayout: CardView = itemView.findViewById<CardView>(R.id.card_layout)
        val title: TextView = itemView.findViewById<TextView>(R.id.tv_title)
        val note: TextView = itemView.findViewById<TextView>(R.id.tv_note)
        val date: TextView = itemView.findViewById<TextView>(R.id.tv_date)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotesRecyclerViewAdapter.NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: NotesRecyclerViewAdapter.NotesViewHolder, position: Int) {
        val currentNote = notesList[position]
        with(holder) {
            title.text = currentNote.title
            note.text = currentNote.note
            date.text = currentNote.date
            title.isSelected = true
            date.isSelected = true

            notesLayout.setCardBackgroundColor(itemView.resources.getColor(randomColor(), null))

            notesLayout.setOnClickListener {
                listener.onNoteClicked(notesList[adapterPosition])
            }
            notesLayout.setOnLongClickListener {
                listener.onNoteLongClicked(notesList[adapterPosition], notesLayout)
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun updateList(newList: List<EntityNote>) {
        fullNotesList.clear()
        fullNotesList.addAll(newList)
        notesList.clear()
        notesList.addAll(fullNotesList)
        notifyDataSetChanged()
    }

    fun filterList(search : String) {
        notesList.clear()
        for(item in fullNotesList) {
            if(item.title?.lowercase()?.contains(search.lowercase()) == true || item.note?.lowercase()?.contains(search.lowercase()) == true) {
                notesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    private fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    interface NotesClickListener {
        fun onNoteClicked(note: EntityNote)
        fun onNoteLongClicked(note: EntityNote, cardView: CardView)
    }
}