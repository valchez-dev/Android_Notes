package my.personal.notes.screens

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import my.personal.notes.R
import my.personal.notes.database.model.Note
import my.personal.notes.database.viewmodel.NoteViewModel
import my.personal.notes.databinding.FragmentDetailsBinding
import java.text.SimpleDateFormat
import java.util.*

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
            Toast.makeText(context, "Note Saved", Toast.LENGTH_SHORT).show()
        }

        //back icon-button
        binding.btnBack.setOnClickListener {
            navController.navigate(R.id.action_detailsFragment_to_homeFragment)
        }

        //color-picked btn to add


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}