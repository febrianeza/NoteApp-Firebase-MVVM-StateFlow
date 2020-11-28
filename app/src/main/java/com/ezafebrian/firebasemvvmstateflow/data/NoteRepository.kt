package com.ezafebrian.firebasemvvmstateflow.data

import com.ezafebrian.firebasemvvmstateflow.data.model.Note
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class NoteRepository {

    private val db = Firebase.firestore

    private val _notesState = MutableStateFlow<NoteState>(NoteState.Empty)
    internal val notesState: StateFlow<NoteState> = _notesState

    private val _saveNotesState = MutableStateFlow<NoteState>(NoteState.Empty)
    internal val saveNotesState: StateFlow<NoteState> = _saveNotesState

    private val _updateNotesState = MutableStateFlow<NoteState>(NoteState.Empty)
    internal val updateNotesState: StateFlow<NoteState> = _updateNotesState

    private val _deleteNoteState = MutableStateFlow<NoteState>(NoteState.Empty)
    internal val deleteNoteState: StateFlow<NoteState> = _deleteNoteState

    fun listenNotes() {
        db.collection("notes").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    _notesState.value = NoteState.Error("Listen failed")
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val notes = snapshot.toObjects(Note::class.java)
                    _notesState.value = NoteState.Success(notes)

                    if (notes.isEmpty())
                        _notesState.value = NoteState.Empty
                }
            }
    }

    fun saveNote(note: Note) {
        _saveNotesState.value = NoteState.Loading
        val noteDocRef = db.collection("notes").document()

        note.noteId = noteDocRef.id
        noteDocRef.set(note)
            .addOnSuccessListener {
                _saveNotesState.value = NoteState.Success(it)
            }.addOnFailureListener {
                _saveNotesState.value = NoteState.Error("Add note failed")
                Timber.e(it)
            }
    }

    fun updateNote(note: Note) {
        _updateNotesState.value = NoteState.Loading
        val noteDocRef = db.collection("notes").document(note.noteId)

        noteDocRef.update(
            "title", note.title,
            "content", note.content,
            "date", note.date
        ).addOnSuccessListener {
            _updateNotesState.value = NoteState.Success(it)
        }.addOnFailureListener {
            _updateNotesState.value = NoteState.Error("Update note failed")
            Timber.e(it)
        }
    }

    fun deleteNote(noteId: String) {
        _deleteNoteState.value = NoteState.Loading
        db.collection("notes").document(noteId).delete()
            .addOnSuccessListener { _deleteNoteState.value = NoteState.Success(it) }
            .addOnFailureListener {
                _deleteNoteState.value = NoteState.Error("Delete note failed")
                Timber.e(it)
            }
    }
}