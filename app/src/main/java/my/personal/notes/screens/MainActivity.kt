package my.personal.notes.screens

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import my.personal.notes.database.db.NoteDatabase
import my.personal.notes.database.repo.NoteRepository
import my.personal.notes.database.viewmodel.NoteViewModel
import my.personal.notes.database.viewmodel.NoteViewModelFactory
import my.personal.notes.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //Initialise the ViewModel
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]


    }



//    private fun initViewModel() {
//
//        val repo = NoteRepository(NoteDatabase(this))
//        val factory = NoteViewModelFactory(repo)
//        viewModel = ViewModelProvider(this, factory).get(NoteViewModel::class.java)
//    }
}