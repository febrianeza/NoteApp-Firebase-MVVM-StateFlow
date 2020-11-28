package com.ezafebrian.firebasemvvmstateflow.ui.note

import androidx.lifecycle.ViewModel
import com.ezafebrian.firebasemvvmstateflow.data.NoteRepository
import com.ezafebrian.firebasemvvmstateflow.data.NoteState
import kotlinx.coroutines.flow.StateFlow

class NoteViewModel : ViewModel() {
    private val repository = NoteRepository()

    val notesState: StateFlow<NoteState> = repository.notesState

    init {
        repository.listenNotes()
    }
}