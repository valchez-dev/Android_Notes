package my.personal.notes.recyclerview

import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.personal.notes.R
import my.personal.notes.database.model.Note

class NoteRvAdapter(
    val context: Context,
    val noteClickInterface: NoteClickInterface
    ) : RecyclerView.Adapter<NoteRvAdapter.ViewHolder>()  {


    private val allNotes = ArrayList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.setText(allNotes.get(position).title)
        holder.body.setText(allNotes.get(position).body)
        holder.date.setText("Last changed: " + allNotes.get(position).date)

        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }


    //update the list
    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.card_title)
        val body = view.findViewById<TextView>(R.id.card_body)
        val date = view.findViewById<TextView>(R.id.card_date)
    }


}




//clicked item
interface NoteClickInterface {
    fun onNoteClick(note:Note)
}

