package my.personal.notes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import my.personal.notes.R
import my.personal.notes.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Navigation
        val navController: NavController = Navigation.findNavController(view)

        //save icon-button
        binding.btnDone.setOnClickListener {
            saveNote()
        }

        //back icon-button
        binding.btnBack.setOnClickListener {
            navController.navigate(R.id.action_detailsFragment_to_homeFragment)
        }


    }


    private fun saveNote() {

        //title cant' be empty
        if (binding.etTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Title can't be empty", Toast.LENGTH_SHORT).show()
        }

/*
        //coroutined
        launch {

            var note = Note()

            note.title = binding.etTitle.text.toString()
            note.body = binding.etBody.text.toString()
            note.date = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.ENGLISH).format(Date()).toString()

            context?.let {
                NoteDatabase.getDatabase(it).noteDao().addNote(note)
            }

        }
        binding.etTitle.setText("")
        binding.etBody.setText("")*/
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}