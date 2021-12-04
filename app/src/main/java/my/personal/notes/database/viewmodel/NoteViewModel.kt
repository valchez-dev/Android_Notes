package my.personal.notes.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.personal.notes.database.db.NoteDatabase
import my.personal.notes.database.model.Note
import my.personal.notes.database.repo.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val allNotes: LiveData<List<Note>>
    private val repo: NoteRepository


    init {
        val dao = NoteDatabase.getDatabase(application).noteDao()
        repo = NoteRepository(dao)
        allNotes = repo.getNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteNote(note)
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo.insertNote(note)
    }

    fun updateNote(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repo.updateNote(id)
    }


}