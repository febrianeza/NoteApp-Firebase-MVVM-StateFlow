package com.ezafebrian.firebasemvvmstateflow.ui.save

import androidx.lifecycle.ViewModel
import com.ezafebrian.firebasemvvmstateflow.data.NoteRepository
import com.ezafebrian.firebasemvvmstateflow.data.NoteState
import com.ezafebrian.firebasemvvmstateflow.data.model.Note
import kotlinx.coroutines.flow.StateFlow

class SaveViewModel : ViewModel() {
    private val repository = NoteRepository()

    val saveNotesState: StateFlow<NoteState> = repository.saveNotesState

    fun saveNote(note: Note) {
        repository.saveNote(note)
    }
}