package my.personal.notes.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import my.personal.notes.database.dao.NoteDao
import my.personal.notes.database.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    //get dao
    abstract fun getNoteDao(): NoteDao

    companion object {

        @Volatile
        private var instance: NoteDatabase? = null
        private val LOCK = Any()

        private fun createDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "notes_database"
            ).build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDB(context).also {
                instance = it
            }
        }
    }





}



/*
    companion object {
        private var INSTANCE: NoteDatabase? = null
        fun getDatabase(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,NoteDatabase::class.java,
                            "notes_database")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }


 */