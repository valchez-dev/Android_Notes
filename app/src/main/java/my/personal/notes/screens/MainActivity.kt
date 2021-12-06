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

    private lateinit var binding: ActivityMainBinding
    lateinit var activityViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //creating the instance of db
        try{
            val repo = NoteRepository(NoteDatabase(this))
            val factory = NoteViewModelFactory(repo)
            activityViewModel = ViewModelProvider(this,factory)[NoteViewModel::class.java]
        }catch (e: Exception){
            Log.d("MainActivity", "Error with accessing the ViewModel")
        }






    }
}