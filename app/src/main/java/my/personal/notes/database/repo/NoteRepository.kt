package my.personal.notes.database.repo

import my.personal.notes.database.db.NoteDatabase
import my.personal.notes.database.model.Note

class NoteRepository(private val db: NoteDatabase) {

    fun getAllNotes() = db.getNoteDao().getNotes()
    fun searchNote(query: String) = db.getNoteDao().searchNote(query)

    suspend fun insertNote(note: Note) = db.getNoteDao().addNote(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

}