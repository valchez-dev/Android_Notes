package my.personal.notes.database.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.personal.notes.database.model.Note
import my.personal.notes.database.repo.NoteRepository

class NoteViewModel(
    private val repo: NoteRepository
) : ViewModel() {


    fun saveNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo.updateNote(note)
    }

    fun searchNote(query: String): LiveData<List<Note>> = repo.searchNote(query)
    fun getAllNotes(): LiveData<List<Note>> = repo.getAllNotes()

}