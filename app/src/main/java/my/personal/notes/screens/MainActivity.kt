package my.personal.notes.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import my.personal.notes.database.db.NoteDatabase
import my.personal.notes.database.repo.NoteRepository
import my.personal.notes.database.viewmodel.NoteViewModel
import my.personal.notes.database.viewmodel.NoteViewModelFactory
import my.personal.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    lateinit var viewModel: NoteViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        try{
            setContentView(binding.root)
            val repo = NoteRepository(NoteDatabase(this))
            val factory = NoteViewModelFactory(repo)
            viewModel = ViewModelProvider(this,factory)[NoteViewModel::class.java]

        }catch (e: Exception){
            Log.d("TAG", "ERROR")
        }

    }
}