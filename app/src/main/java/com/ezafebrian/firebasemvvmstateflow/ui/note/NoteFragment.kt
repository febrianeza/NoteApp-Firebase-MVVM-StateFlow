package com.ezafebrian.firebasemvvmstateflow.ui.note

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ezafebrian.firebasemvvmstateflow.R
import com.ezafebrian.firebasemvvmstateflow.data.NoteState
import com.ezafebrian.firebasemvvmstateflow.data.model.Note
import com.ezafebrian.firebasemvvmstateflow.databinding.NoteFragmentBinding
import kotlinx.coroutines.flow.collect

@Suppress("UNCHECKED_CAST")
class NoteFragment : Fragment(R.layout.note_fragment) {

    private var _binding: NoteFragmentBinding? = null
    private val binding get() = _binding
    private val noteAdapter = NoteAdapter()

    private val viewModel: NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = NoteFragmentBinding.bind(view)

        binding?.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = noteAdapter

            listenToNotes(binding!!)

            buttonAddNote.setOnClickListener {
                findNavController().navigate(R.id.action_noteFragment_to_saveFragment)
            }

            noteAdapter.onItemClick = { note ->
                // edit
                val directions = NoteFragmentDirections.actionNoteFragmentToEditFragment(note)
                findNavController().navigate(directions)
            }

            noteAdapter.onItemLongClick = { note ->
                // delete
                val directions = NoteFragmentDirections.actionNoteFragmentToDeleteFragment(note.noteId)
                findNavController().navigate(directions)
            }
        }
    }

    private fun listenToNotes(binding: NoteFragmentBinding) {
        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.notesState.collect {
                    when (it) {
                        is NoteState.Success<*> -> {
                            progressBar.visibility = View.GONE
                            textEmpty.visibility = View.GONE

                            val notes = it.data as List<Note>
                            noteAdapter.populateNotes(notes)
                        }
                        is NoteState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            textEmpty.visibility = View.GONE
                        }
                        is NoteState.Error -> {
                            progressBar.visibility = View.GONE
                            textEmpty.visibility = View.GONE

                            Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                        is NoteState.Empty -> {
                            progressBar.visibility = View.GONE
                            textEmpty.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}