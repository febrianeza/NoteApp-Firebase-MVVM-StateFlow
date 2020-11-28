package com.ezafebrian.firebasemvvmstateflow.ui.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ezafebrian.firebasemvvmstateflow.data.NoteState
import com.ezafebrian.firebasemvvmstateflow.databinding.DeleteFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect

class DeleteFragment : BottomSheetDialogFragment() {

    private var _binding: DeleteFragmentBinding? = null
    private val binding get() = _binding

    private val viewModel: DeleteViewModel by viewModels()
    private val args: DeleteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DeleteFragmentBinding.inflate(LayoutInflater.from(context))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
            buttonCancel.setOnClickListener {
                dismiss()
            }

            buttonDelete.setOnClickListener {
                viewModel.deleteNote(args.noteId)
            }

            lifecycleScope.launchWhenStarted {
                viewModel.deleteNoteState.collect {
                    when (it) {
                        is NoteState.Success<*> -> dismiss()
                        is NoteState.Error -> {
                            progressBar.visibility = View.GONE

                            Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                        is NoteState.Loading -> progressBar.visibility = View.VISIBLE
                        else -> Unit
                    }
                }
            }
        }
    }
}