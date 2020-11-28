package com.ezafebrian.firebasemvvmstateflow.ui.edit

import androidx.lifecycle.ViewModel
import com.ezafebrian.firebasemvvmstateflow.data.NoteRepository
import com.ezafebrian.firebasemvvmstateflow.data.NoteState
import com.ezafebrian.firebasemvvmstateflow.data.model.Note
import kotlinx.coroutines.flow.StateFlow

class EditViewModel : ViewModel() {
    private val repository = NoteRepository()

    val updateNoteState: StateFlow<NoteState> = repository.updateNotesState

    fun update(note: Note) {
        repository.updateNote(note)
    }
}