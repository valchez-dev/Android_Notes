package my.personal.notes.database.repo

import androidx.lifecycle.LiveData
import my.personal.notes.database.dao.NoteDao
import my.personal.notes.database.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val getNotes: LiveData<List<Note>> = noteDao.getNotes()

    suspend fun insertNote(note: Note) {
        noteDao.addNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun updateNote(noteID: Int){
        noteDao.updateNote(noteID)
    }


}