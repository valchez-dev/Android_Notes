package my.personal.notes.screens

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.personal.notes.R
import my.personal.notes.database.model.Note
import my.personal.notes.database.viewmodel.NoteViewModel
import my.personal.notes.databinding.BottomSheetBinding
import my.personal.notes.databinding.FragmentDetailsBinding
import my.personal.notes.utils.hideKeyboard
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private val viewModel: NoteViewModel by activityViewModels()
    private val args: DetailsFragmentArgs by navArgs()

    private var note: Note? = null
    private var color = -1
    private val currentDate =
        SimpleDateFormat.getInstance().format(Date()) //or getDateInstance()?


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_container //TODO: not sure which to set
            scrimColor = Color.TRANSPARENT
            duration = 300L
        }

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

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

        //Access to activity
        val activity = activity as MainActivity


        //navigation transition name
        ViewCompat.setTransitionName(
            binding.detailsFragment,
            "list_${args.note?.id}"
        )

        //back button
        binding.detailsBtnBack.setOnClickListener {
            requireView().hideKeyboard()
            navController.popBackStack()
        }


        //date showing title
        binding.detailsDate.setText("Edited: " + currentDate)


        //color picker btn
        binding.detailsBtnColorPicker.setOnClickListener {

            val bottomSheetDialog = BottomSheetDialog(
                requireContext(),
                R.style.BottomSheetDialogTheme
            )

            val bottomSheetView: View = layoutInflater.inflate(
                R.layout.bottom_sheet,
                null
            )
            with(bottomSheetDialog) {
                setContentView(bottomSheetView)
                show()
            }

            val bottomSheetBinding = BottomSheetBinding.bind(bottomSheetView)
            bottomSheetBinding.apply {
                colorsPicker.apply {
                    setSelectedColor(color) //in-built method of lib
                    setOnColorSelectedListener { value ->
                        color = value
                        binding.apply {
                            detailsFragment.setBackgroundColor(color)
                            detailsToolbar.setBackgroundColor(color)
                            activity.window.statusBarColor = color
                        }

                        bottomSheetBinding.bottomSheetCard.setBackgroundColor(color)

                    }
                }
                bottomSheetContainer.setBackgroundColor(color)
            }

            bottomSheetView.post {
                bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }


        //save button
        binding.detailsBtnSave.setOnClickListener {
            requireView().hideKeyboard()
            saveNote()
        }



        //existing data
        setUpExistingNote()

    }


    private fun setUpExistingNote(){

        val note = args.note

        if (note == null){
            binding.detailsDate.text = "Edited: " + currentDate
        }else{

            binding.detailsEtTitle.setText(note.title)
            binding.detailsEtBody.setText(note.body)
            binding.detailsDate.setText("Edited: " + currentDate)
            color = note.color

            binding.apply {
                    detailsFragment.setBackgroundColor(color)
                    detailsToolbar.setBackgroundColor(color)
            }
            activity?.window?.statusBarColor = note.color
        }
    }


    private fun saveNote() {

        //Title can not be empty
        if (binding.detailsEtTitle.text.toString().isEmpty()) {
            Toast.makeText(context, "Title required", Toast.LENGTH_SHORT).show()
        } else {

            note = args.note

            //If null -> create
            //If exists -> update
            when (note) {
                null -> {
                    viewModel.saveNote(
                        Note(
                            0,
                            binding.detailsEtTitle.text.toString(),
                            binding.detailsEtBody.text.toString(),
                            currentDate,
                            color
                        )
                    )
                    navController.navigate(DetailsFragmentDirections.actionDetailsFragmentToHomeFragment())
                }

                else -> {
                    updateNote()
                    navController.popBackStack()
                }
            }


        }
    }


    private fun updateNote() {

        if (note != null) {
            viewModel.updateNote(
                Note(
                    note!!.id,
                    binding.detailsEtTitle.text.toString(),
                    binding.detailsEtBody.text.toString(),
                    currentDate,
                    color
                )
            )
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}