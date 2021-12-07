package my.personal.notes.screens

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.personal.notes.R
import my.personal.notes.database.viewmodel.NoteViewModel
import my.personal.notes.databinding.FragmentHomeBinding
import my.personal.notes.recyclerview.NoteRvAdapter

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //private val viewModel: NoteViewModel by activityViewModels()
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialElevationScale(false).apply {
            duration = 350
        }
        enterTransition = MaterialElevationScale(true).apply {
            duration = 350
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //access to main activity
        val activity = activity as MainActivity
        viewModel = activity.viewModel


        //navigation
        val navController: NavController = Navigation.findNavController(view)

        CoroutineScope(Dispatchers.Main).launch {
            delay(10)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.parseColor("#9E9D9D")
        }

        //add-note button clicked
        binding.fabHome.setOnClickListener {
            binding.appBarLayout.visibility = View.INVISIBLE
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment())
        }




    }







    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}