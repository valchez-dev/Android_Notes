package my.personal.notes.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import my.personal.notes.database.dao.NoteDao
import my.personal.notes.database.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {


    companion object {
        var db: NoteDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): NoteDatabase {
            if (db != null) {
                db = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                    "Notes.db"
                ).build()
            }
            return db!!
        }
    }

    //get dao
     abstract fun noteDao(): NoteDao


}