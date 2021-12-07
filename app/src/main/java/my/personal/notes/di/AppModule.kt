package my.personal.notes.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.personal.notes.database.dao.NoteDao
import my.personal.notes.database.db.NoteDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getNoteDatabase(context: Application): NoteDatabase {
        return NoteDatabase.getNoteDatabase(context)
    }

    @Singleton
    @Provides
    fun getNoteDAO(db: NoteDatabase): NoteDao {
        return db.getNoteDao()
    }
}