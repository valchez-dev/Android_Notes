package my.personal.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import my.personal.notes.R
import my.personal.notes.database.model.Note
import my.personal.notes.databinding.CardBinding
import my.personal.notes.screens.HomeFragmentDirections
import my.personal.notes.utils.hideKeyboard

class NoteAdapter : ListAdapter<Note, NoteAdapter.NotesViewHolder>(DiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return NotesViewHolder(view)
    }



    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        getItem(position).let{ note ->

            holder.apply {

                itemView.transitionName = "list_${note.id}"
                title.text = note.title
                body.text = note.body
                date.text = note.date
                card.setCardBackgroundColor(note.color)


                //user tapped some card
                card.setOnClickListener{
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment().setNote(note)
                    val extras = FragmentNavigatorExtras(itemView to "list_${note.id}")

                    it.hideKeyboard()

                    Navigation.findNavController(it).navigate(action,extras)
                }
            }
        }
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = CardBinding.bind(itemView)

        val title: MaterialTextView = binding.cardTitle
        val body: TextView = binding.cardBody
        val date: MaterialTextView = binding.cardDate
        val card: MaterialCardView = binding.card
    }
}

