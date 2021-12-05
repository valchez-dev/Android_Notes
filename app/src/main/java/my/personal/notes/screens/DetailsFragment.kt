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

    private lateinit var navController: NavController

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
        binding.detailsBtnSave.setOnClickListener {
            saveNote()
            Toast.makeText(context, "Note Saved", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.action_detailsFragment_to_homeFragment)
        }

        //back icon-button
        binding.detailsBtnBack.setOnClickListener {
            navController.popBackStack()
        }


    }


    private fun saveNote() {
        if (binding.detailsEtTitle.text.toString().isNullOrEmpty()) {
            Toast.makeText(context, "Title required", Toast.LENGTH_SHORT).show()
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}