package my.personal.notes.database.repo

import my.personal.notes.database.dao.NoteDao
import my.personal.notes.database.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: NoteDao) {

    fun getAllNotes() = dao.getNotes()
    fun searchNote(query: String) = dao.searchNote(query)

    suspend fun insertNote(note: Note) = dao.addNote(note)
    suspend fun deleteNote(note: Note) = dao.deleteNote(note)
    suspend fun updateNote(note: Note) = dao.updateNote(note)
}