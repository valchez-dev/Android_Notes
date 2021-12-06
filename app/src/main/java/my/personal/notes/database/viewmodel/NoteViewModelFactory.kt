package my.personal.notes.database.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import my.personal.notes.database.repo.NoteRepository

class NoteViewModelFactory(
    private val repo: NoteRepository
) : ViewModelProvider.Factory {

    //TODO: or .NewInstanceFactory ?

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(repo) as T
    }
}