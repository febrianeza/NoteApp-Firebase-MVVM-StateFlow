package com.ezafebrian.firebasemvvmstateflow.data

sealed class NoteState {
    data class Success<T>(val data: T? = null) : NoteState()
    data class Error(val message: String?) : NoteState()
    object Loading : NoteState()
    object Empty : NoteState()
}
