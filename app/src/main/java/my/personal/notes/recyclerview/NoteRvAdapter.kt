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
    private val list: ArrayList<Note>
    ) : RecyclerView.Adapter<NoteRvAdapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title.text = list[position].title
        holder.body.text = list[position].body
        holder.date.text = "Last changed: " + list[position].date

    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.card_title)
        val body = view.findViewById<TextView>(R.id.card_body)
        val date = view.findViewById<TextView>(R.id.card_date)
    }


}

