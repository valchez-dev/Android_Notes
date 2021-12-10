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
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
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
import my.personal.notes.utils.SwipeToDelete
import my.personal.notes.utils.hideKeyboard
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by activityViewModels()
    private lateinit var listAdapter: NoteAdapter



    //animations
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialElevationScale(false).apply {
            duration = 350
        }
        enterTransition = MaterialElevationScale(true).apply {
            duration = 350
        }
    }

    //binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //access to activity
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
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment())
        }

        //RecyclerView
        showRecyclerView()
        swipeToDelete(binding.rvHome)


        //search bar listener
        binding.homeSearch.addTextChangedListener(object : TextWatcher {

            //removing no-notes placeholder while typing
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.homeTextPlaceholder.isVisible = false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    val input = s.toString()
                    val query = "%$input%"
                    if (query.isNotEmpty()) {
                        viewModel.searchNote(query).observe(viewLifecycleOwner) {
                            listAdapter.submitList(it)
                        }
                    } else {
                        observerDataChanges()
                    }
                } else {
                    observerDataChanges()
                }
            }

            //im not gonna use it
            override fun afterTextChanged(s: Editable?) {}
        })


        //remove the keyboard when user is done with the search bar
        binding.homeSearch.setOnEditorActionListener { myView, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                myView.clearFocus()
                requireView().hideKeyboard()
            }
            return@setOnEditorActionListener true
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {

        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = listAdapter.currentList[position]

                viewModel.deleteNote(note)

                binding.homeSearch.apply {
                    hideKeyboard()
                    clearFocus()
                }

                //update the list
                if (binding.homeSearch.text.isEmpty()) {
                    observerDataChanges()
                }

                //undo the changes by snackbar
                val snackbar = Snackbar.make(
                    requireView(), "Note removed", Snackbar.LENGTH_LONG
                )
                    .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {

                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                        }

                        override fun onShown(transientBottomBar: Snackbar?) {

                            transientBottomBar?.setAction("UNDO") {
                                viewModel.saveNote(note)
                                binding.homeTextPlaceholder.isVisible = false
                            }
                            super.onShown(transientBottomBar)
                        }
                    }).apply {
                        animationMode = Snackbar.ANIMATION_MODE_FADE
                        setAnchorView(R.id.fab_home)
                    }

                snackbar.setActionTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.orange
                    )
                )
                snackbar.show()
            }
        }

        //attach itemTouchHelper to listView
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    private fun showRecyclerView() {

        //portrait vs landscape
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> setupRecyclerView(2) //two-column mode
            Configuration.ORIENTATION_LANDSCAPE -> setupRecyclerView(3) //three-column mode
            else -> setupRecyclerView(2)
        }
    }

    private fun setupRecyclerView(columnCount: Int) {

        binding.rvHome.apply {
            layoutManager =
                StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            listAdapter = NoteAdapter()
            adapter = listAdapter

            //return to listview animation when note is closed
            postponeEnterTransition(300L, TimeUnit.MILLISECONDS)
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
        observerDataChanges()
    }

    private fun observerDataChanges() {
        viewModel.getAllNotes().observe(viewLifecycleOwner) { list ->
            binding.homeTextPlaceholder.isVisible = list.isEmpty()
            binding.homeSearch.isInvisible = list.isEmpty()
            listAdapter.submitList(list)
        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}