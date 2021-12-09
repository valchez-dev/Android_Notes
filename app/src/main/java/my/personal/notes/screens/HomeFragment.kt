package my.personal.notes.screens

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ListAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.personal.notes.R
import my.personal.notes.adapter.NoteAdapter
import my.personal.notes.database.viewmodel.NoteViewModel
import my.personal.notes.databinding.FragmentHomeBinding
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by activityViewModels()
    private lateinit var listAdapter: NoteAdapter




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


        //recycler view initializer
        showRecyclerView()


        //search bar listener
        binding.homeSearch.addTextChangedListener( object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()){
                    val input = s.toString()
                    val query = "%$input%"
                    if(query.isNotEmpty()){
                        viewModel.searchNote(query).observe(viewLifecycleOwner){

                        }
                    }else{
                        observerDataChanges()
                    }
                }else{
                    observerDataChanges()
                }
            }

            //not gonna use it
            override fun afterTextChanged(s: Editable?) {}
        })





    }






    private fun observerDataChanges() {
        viewModel.getAllNotes().observe(viewLifecycleOwner){ list ->
            binding.homeTextPlaceholder.isVisible = list.isEmpty()
            listAdapter.submitList(list)
        }
    }


    private fun showRecyclerView(){

        //portrait vs landscape
        when(resources.configuration.orientation){
            Configuration.ORIENTATION_PORTRAIT -> setupRecyclerView(2) //two-column mode
            Configuration.ORIENTATION_LANDSCAPE -> setupRecyclerView(3) //single-column mode
        }
    }

    private fun setupRecyclerView(columnCount: Int) {


        binding.rvHome.apply {
            layoutManager = StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            listAdapter = NoteAdapter()
            adapter = listAdapter
            postponeEnterTransition(300L, TimeUnit.MILLISECONDS)
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }

        observerDataChanges()


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}