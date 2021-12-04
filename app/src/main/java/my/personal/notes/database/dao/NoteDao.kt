package my.personal.notes.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import my.personal.notes.database.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id LIKE :noteId")
    suspend fun updateNote(noteId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)


}