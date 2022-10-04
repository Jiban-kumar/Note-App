package com.example.note.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.note.entity.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note :Note)

    @Delete
    suspend fun  delete(note: Note)

    @Query("SELECT * FROM note")
    fun getallNote() : LiveData<List<Note>>
}