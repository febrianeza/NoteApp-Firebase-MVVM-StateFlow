package com.ezafebrian.firebasemvvmstateflow.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    @SerializedName("noteId")
    var noteId: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("content")
    var content: String = "",
    @SerializedName("date")
    var date: String = ""
) : Parcelable