package my.personal.notes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import my.personal.notes.R

import my.personal.notes.databinding.FragmentHomeBinding


/*
* Title visibility => gone after few sec
*
*
*
*
*/

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigation to details fragment
        val navController: NavController = Navigation.findNavController(view)
        binding.fabHome.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_detailsFragment)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}