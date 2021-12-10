package my.personal.notes.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import my.personal.notes.database.dao.NoteDao
import my.personal.notes.database.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    //get dao
    abstract fun getNoteDao(): NoteDao

    companion object {

        private var INSTANCE: NoteDatabase? = null

        fun getNoteDatabase(context: Context): NoteDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_db"
                ).build()
            }
            return INSTANCE!!
        }
    }
}
