package com.ezafebrian.firebasemvvmstateflow.ui.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ezafebrian.firebasemvvmstateflow.data.NoteState
import com.ezafebrian.firebasemvvmstateflow.data.model.Note
import com.ezafebrian.firebasemvvmstateflow.databinding.SaveFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect
import java.util.*

class SaveFragment : BottomSheetDialogFragment() {

    private var _binding: SaveFragmentBinding? = null
    private val binding get() = _binding

    private val viewModel: SaveViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SaveFragmentBinding.inflate(LayoutInflater.from(context))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.saveNotesState.collect {
                    when (it) {
                        is NoteState.Success<*> -> dismiss()
                        is NoteState.Error -> {
                            progressBar.visibility = View.GONE

                            Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                        NoteState.Loading -> progressBar.visibility = View.VISIBLE
                        else -> Unit
                    }
                }
            }

            buttonSaveNote.setOnClickListener {
                val noteTitle = etNoteTitle.text.toString().trim()
                val noteText = etNoteText.text.toString().trim()
                val noteDate = Calendar.getInstance().time.toString()

                if (noteTitle.isNotEmpty() && noteText.isNotEmpty()) {
                    viewModel.saveNote(
                        Note(
                            title = noteTitle,
                            content = noteText,
                            date = noteDate
                        )
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}