package com.ezafebrian.firebasemvvmstateflow.ui.delete

import androidx.lifecycle.ViewModel
import com.ezafebrian.firebasemvvmstateflow.data.NoteRepository
import com.ezafebrian.firebasemvvmstateflow.data.NoteState
import kotlinx.coroutines.flow.StateFlow

class DeleteViewModel : ViewModel() {
    private val repository = NoteRepository()

    val deleteNoteState: StateFlow<NoteState> = repository.deleteNoteState

    fun deleteNote(noteId: String) {
        repository.deleteNote(noteId)
    }
}