package my.personal.notes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import my.personal.notes.R
import my.personal.notes.database.model.Note
import my.personal.notes.database.viewmodel.NoteViewModel
import my.personal.notes.databinding.FragmentDetailsBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private var note: Note? = null
    private var color = -1
    private val viewModel: NoteViewModel by activityViewModels()
    private val currentDate = SimpleDateFormat.getInstance().format(Date())
    private val job = CoroutineScope(Dispatchers.IO)
    private val args: DetailsFragmentArgs by navArgs()

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
        navController = Navigation.findNavController(view)

        //save icon-button
        binding.btnDone.setOnClickListener {
            saveNote()
            Toast.makeText(context, "Note Saved", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.action_detailsFragment_to_homeFragment)
        }

        //back icon-button
        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }




    }



    private fun saveNote(){
        if(binding.etTitle.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Title required", Toast.LENGTH_SHORT).show()
        }



    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}