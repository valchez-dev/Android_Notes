package my.personal.notes.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import my.personal.notes.database.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getNotes(): LiveData<List<Note>>

    @Update
    suspend fun updateNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes_table WHERE title LIKE :query OR body LIKE :query ORDER BY id DESC")
    fun searchNote(query: String): LiveData<List<Note>>


}