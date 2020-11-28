package com.ezafebrian.firebasemvvmstateflow.ui.edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ezafebrian.firebasemvvmstateflow.data.NoteState
import com.ezafebrian.firebasemvvmstateflow.data.model.Note
import com.ezafebrian.firebasemvvmstateflow.databinding.EditFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect
import java.util.*

class EditFragment : BottomSheetDialogFragment() {

    private val args: EditFragmentArgs by navArgs()
    private var _binding: EditFragmentBinding? = null
    private val binding get() = _binding

    private val viewModel: EditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EditFragmentBinding.inflate(LayoutInflater.from(context))
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
            args.note.apply {
                etNoteTitle.setText(title)
                etNoteText.setText(content)
                textNoteDate.text = "Last modified: $date"

                buttonUpdateNote.setOnClickListener {
                    val title = etNoteTitle.text.toString()
                    val text = etNoteText.text.toString()
                    val date = Calendar.getInstance().time.toString()

                    viewModel.update(
                        Note(
                            title = title,
                            content = text,
                            date = date,
                            noteId = noteId
                        )
                    )
                }
            }

            lifecycleScope.launchWhenStarted {
                viewModel.updateNoteState.collect {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}