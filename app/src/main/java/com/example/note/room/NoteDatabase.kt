package com.example.note.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.note.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase(){

    abstract fun noteDao():NoteDao

    companion object{

        @Volatile
        private var instance :NoteDatabase? =null

        fun getDatabase(context: Context) : NoteDatabase{
            if(instance == null){
                synchronized(this){
                    instance = Room.databaseBuilder(context.applicationContext, NoteDatabase::class.java, "noteDB").build()
                }
            }
            return instance!!
        }
    }
}