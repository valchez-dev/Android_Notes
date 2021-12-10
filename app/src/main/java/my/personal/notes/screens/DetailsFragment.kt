package my.personal.notes.screens

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private val currentDateTime = SimpleDateFormat("dd/MM/yyyy, HH:mm:ss", Locale.getDefault()).format(Date());



    //opening and closing with animations
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_container  //not sure which to set
            scrimColor = Color.TRANSPARENT
            duration = 300L
        }

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    //binding
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

        //navigation transition name
        ViewCompat.setTransitionName(
            binding.detailsFragment,
            "list_${args.note?.id}"
        )

        //Access to Activity
        val activity = activity as MainActivity

        //basic date title = current date
        binding.detailsDate.text = currentDateTime

        //If note already exists
        setUpExistingNote()




        //navigate-back button
        binding.detailsBtnBack.setOnClickListener {
            requireView().hideKeyboard()
            navController.popBackStack()
        }

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
    }


    private fun setUpExistingNote() {

        //received note-data
        val note = args.note

        if (note == null) {
            binding.detailsDate.text = currentDateTime
        } else {

            binding.apply {
                detailsEtTitle.setText(note.title)
                detailsEtBody.setText(note.body)
                detailsDate.text = currentDateTime
                detailsFragment.setBackgroundColor(note.color)
                detailsToolbar.setBackgroundColor(note.color)
            }
            activity?.window?.statusBarColor = note.color
        }
    }


    private fun saveNote() {

        //Title can't be empty
        if (binding.detailsEtTitle.text.toString().isEmpty()) {
            Toast.makeText(context, "Title required", Toast.LENGTH_LONG).show()
        } else {

            note = args.note

            //null -> create / exists -> update
            when (note) {
                null -> {
                    viewModel.saveNote(
                        Note(
                            0,
                            binding.detailsEtTitle.text.toString(),
                            binding.detailsEtBody.text.toString(),
                            currentDateTime,
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
                    currentDateTime,
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